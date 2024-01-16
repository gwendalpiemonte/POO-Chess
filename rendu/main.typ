#import "fen.typ": *
#import "@preview/cetz:0.1.2"
#import "file_utils.typ": *

#show link: underline

= Schéma de classes


== Architecture et choix d'implémentations

=== Bitboards

Nous avons décider d'utiliser une approche faisant usage de bitboards #footnote[https://www.chessprogramming.org/Bitboards]. L'avantage de cette approche est qu'il nous est très facile, à l'aide des méthodes disponibles sur un `Bitboard` (and, or, xor, not), d'efféctuer des opérations pour déterminer les déplacements autorisés, les mises en échec, etc.

=== FEN Notation

Nous avons également décidé, dès le début du projet, d'implémenter un parser FEN #footnote[https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation]. Cela nous à permis de rapidement tester des positions de jeu, à la fois manuellement et automatiquement à l'aide de tests d'intégrations.

Le parser FEN est également utiliser pour initialiser la position par défaut en début de partie. Il est possible de facilement créer un FEN à l'aide d'outils numériques #footnote[https://lichess.org/editor]

=== Structure générale 

L'implémentation dans le package `engine` est structuré en plusieurs packages : 

TODO

=== Pièces

Toutes les pièces concrètes héritent de la classe `Piece`. La classe `Piece` permet de factoriser au maximum le code pour chaques pièces, tel que la couleur ou encore les déplacements légaux de la pièce.

Chaques classes de pièce concret (`King`, `Queen`, `Knight`, `Bishop`, `Rook`, `Pawn`) possède donc une méthode `getMoves` permettant, pour chaque classe, d'implémenter la logique de mouvement propre à ce type de pièce.

=== Board

La classe `Board` gère l'état de la partie. C'est cette classe qui est utilisée dans la classe `ChessGame` mis à disposition pour encapsuler la logique de jeu.

Le `Board` implémente l'interface `Cloneable`, cela permet de facilement vérifier qu'un déplacement n'engendrerait pas un échec.

=== FenParser

La classe `FenParser` permet l'utilisation de la notation FEN. Elle est utilisée dans les tests et pour définir la position par défaut d'une partie.

=== Position

La classe `Position` permet de stocker la position d'une case sur l'échiquier. Elle est également utilisée pour convertir une position String tel que `"a4"` dans le format utilisé par le `Board`.

=== PromotionChoice

La classe `PromotionChoice` implémente l'interface `ChessView.UserChoice`. Cette classe permet de promouvoir un pion vers le type de pièce sélectionné.

== Interprétation

#let inline_arrow = (color) => {
  box(cetz.canvas({
      import cetz.draw: *
      
      line((0.75,0), (0,0), mark: (end: ">", size: 0.25), stroke: 2pt + color)
  }))
}

#grid(columns: (auto, 1fr), gutter: 14pt,
  fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
  ", size: 16pt),
  [
    Le plateau d'échec ci-contre représente l'état du plateau au moment du test, ainsi que l'action tentée.
    - Une flèche jaune ( #inline_arrow(rgb("#f7c149")) ) représente une action (`move`) qui est légal.
    - Une flèche rouge ( #inline_arrow(rgb("#e09594")) ) représente quand à elle une action illégale, qui ne doit pas être acceptée par l'application.

    Le `Nom interne` quand à lui, représente le nom interne de la fonction de tests, permettant au développeur de comprendre la raison du non fonctionnement.

    Le développeur peut aussi rajouter une `Description`, qui sera attachée au test. Cela permet d'expliquer la raison de l'erreur, ou la raison de l'existance du test.
  ]
)



== Tests

#{
  let elements = json("tests-output.json")
  
  let grouped_elements = elements.fold(("ignore": ()), (acc, item) => {
    if acc.at(item.testCase, default: ()) == () {
      acc.insert(item.testCase, ())
    }
    acc.at(item.testCase).push(item);
    acc
  })

  let content = []
  
  for (key, value) in grouped_elements {
    if key == "ignore" {
      continue
    }
  
    content += block()[
      === #key
      
      #value.map(element => {
            test(element.fen, from: element.from, to: element.to, element.description, valid: element.valid, name: element.testCase)
        }).join()
    ] 
  }

  content
}

== Code source

#listing()