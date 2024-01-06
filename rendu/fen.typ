
#import "@preview/tablex:0.0.7": tablex
#import "@preview/cetz:0.1.2"

#let fen(pieces, size: 22pt) = {
  let piece-map = (
    "K": image("pieces/K.svg"), // "\u{2654}"
    "Q": image("pieces/Q.svg"), // "\u{2655}"
    "R": image("pieces/R.svg"), // "\u{2656}"
    "B": image("pieces/B.svg"), // "\u{2657}"
    "N": image("pieces/N.svg"), // "\u{2658}"
    "P": image("pieces/P.svg"), // "\u{2659}"
    "k": image("pieces/k.svg"), // "\u{265a}"
    "q": image("pieces/q.svg"), // "\u{265b}"
    "r": image("pieces/r.svg"), // "\u{265c}"
    "b": image("pieces/b.svg"), // "\u{265d}"
    "n": image("pieces/n.svg"), // "\u{265e}"
    "p": image("pieces/p.svg"), // "\u{265f}"
  )

  let board = ()
  let count = 0
  for c in pieces {
    if c == " " {
      assert(count != 8, message: "count != 8")
      break
    }
    count = "12345678".position(c)
    if count != none {
      for i in range(0, count+1) {
	      board.push(" ")
	      count += 1
      }
    } else if c == "/" {
      assert(count != 7, message: " count")
      count = 0
    } else {
      assert(c in "KQBNRPkqbnrp", message: "bad char")
      board.push(piece-map.at(c))
      count += 1
    }
  }
  assert(board.len() == 64, message: "bad FEN")

  let white = rgb(229,229,229);
  let black = rgb(102,102,102);

  let cell_black = rgb("#779954")
  let cell_white = rgb("#e9edcc")
  
  let cell = rect.with(
    height: 100%,
    width: 100%,
    fill: black,
    inset: 10pt
  )
  let rows = range(0, 8)
  let cols = range(0, 8)
  let blankboard = range(0,64).map(i =>if calc.even(int(i/8)+int(calc.rem(i,2))) {[1]} else [0])
  
  if board == none {
    [board is empty]
  } else {
    return tablex(columns: cols.map(i => size), rows: (cols.map(i => size)),
      map-cells: cell => {
        (..cell, content: cell.content, fill: (cell_white, cell_black).at(calc.rem(cell.y * 9 + cell.x, 2)))
      },
      inset: 0pt,
      stroke: none,
      map-hlines: h => (..h, stroke: if h.y == 0 or h.y == 8 { (paint: black, thickness: 2pt) } else { none }),
      map-vlines: h => (..h, stroke: if h.x == 0 or h.x == 8 { (paint: black, thickness: 2pt) } else { none }),

      ..board
    )
  }
}

#let parseCoords = (coord) => {
  let file = "abcdefgh".position(coord.at(0))
  let rank = "123456789".position(coord.at(1))

  (file, rank)
}

#let test = (fen_notation, from: "a1", to: "b2", name: "unknownTest", valid: true, ..desc) => {
  if to == "" or from == "" {
    return "Error!"
  }

  let size = 16pt;
  
  let color = if valid {
    rgb("#f7c149")
  } else {
    rgb("#e09594")
  }
  block(
    breakable: false,
    grid(columns: (auto, 1fr), gutter: 10pt,
      block[
        #fen(fen_notation, size: size)
        #place(
          top + left,
          cetz.canvas(length: size / 2, {
            import cetz.draw: *
            rect((0,0), (16,16), stroke: none)
      
            set-style(mark: (fill: color))
            if to != from {
              line(parseCoords(from).map(i => i * 2 + 1), parseCoords(to).map(i => i * 2 + 1), mark: (end: ">", size: 0.5), stroke: 4pt + color)
            } else {
              rect(parseCoords(to).map(i => i * 2), parseCoords(to).map(i => i * 2 + 2), stroke: 4pt + color)
            }
          })
        )
      ],
      [
        Nom interne: #raw(block: false, name)
        
        #box(..desc)
      ]
    )
  )
}