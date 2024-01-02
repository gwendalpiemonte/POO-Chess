package engine.move;

import engine.Board;
import engine.Position;

public class DiagonalMove {
   public static boolean isValid(Board board, Position from, Position to) {
       int rankDistance = to.rank() - from.rank();
       int fileDistance = to.file() - from.file();

       if (rankDistance == 0 || fileDistance == 0) {
           return false;
       }

       if (Math.abs(rankDistance) != Math.abs(fileDistance)) {
           return false;
       }

       int fileDir = fileDistance > 0 ? 1 : -1;
       int rankDir = rankDistance > 0 ? 1 : -1;
       for (int i = 1; i < Math.abs(fileDistance); i++) {
           if (board.at(from.file() + i * fileDir, from.rank() + i * rankDir) != null) {
               return false;
           }
       }

       return true;
   }
}
