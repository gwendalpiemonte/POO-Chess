#import "fen.typ": *
#import "@preview/cetz:0.1.2"
#import "file_utils.typ": *

#show link: underline

= Schéma de classes

= Choix de conception

Afin de faciliter les tests, ainsi que de rester autant que possible dans les standards, nous avons choisi de supporter le format FEN (Forsyth–Edwards Notation), écriture standard pour l'état d'un échequier sans stockage des mouvements. Ce choix a été effectué dès le début, et nous a été très utile pour tester le bon fonctionnement, comme expliqué dans la section *Tests* de ce rapport.

Une autre fonctionnalité clef du programme est la class `engine.bitboard.Bitboard`. Comme présenté dans https://www.chessprogramming.org/Bitboards, qui permet de représenter certaines situations, tel que:
- les mouvements autorisés pour les Tours
- Tous les mouvements (pseudo-)légaux d'une pièce
de facon plus succinte et efficiente.

= Tests

Afin de faciliter le développement, et rendre plus faclile les modifications majeures du fonctionnemement du programme, des tests d'intégrations ont étés mis en place.

Cela nous permet de s'assurer que le comportement de l'application est exactement celui attendu.

La présentation dans ce rapport permet de plus facilement interpréter la notation FEN, qui peut être difficile a lire sans outil externe.
Ces notations ont été intégralement générées à l'aide du site #link("https://lichess.org/editor")

== Interprétation

#let inline_arrow = (color) => {
  box(cetz.canvas({
      import cetz.draw: *
      
      line((0.75,0), (0,0), mark: (end: ">", size: 0.25), stroke: 2pt + color)
  }))
}

#grid(columns: (auto, 1fr), gutter: 10pt,
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

/*
#{
  let elements = json("./generated/tests-output.json")

  for element in elements {
    test(element.fen, from: element.from, to: element.to, element.description, valid: element.valid, name: element.testCase)
  }
}
*/

== Code source

#listing()