public class XAgent {

    static int play(Ilayout board) {
        return bestMove(board, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getTurn() == Ilayout.ID.X);
    }


    private static int bestMove(Ilayout board, int alpha, int beta, boolean maximizingPlayer) {
        if (board.isGameOver()) {
            return score(board);
        }

        int bestMove = -1;
        int bestScore = (maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE);

        for (Integer move : board.getAvailableMoves()) {
            Ilayout newBoard = (Ilayout) ((Board)board).clone();
            newBoard.move(move);
            int currentScore = minimax(newBoard, alpha, beta, !maximizingPlayer);

            if (maximizingPlayer) {
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestMove = move;
                }
                alpha = Math.max(alpha, currentScore);
            } else {
                if (currentScore < bestScore) {
                    bestScore = currentScore;
                    bestMove = move;
                }
                beta = Math.min(beta, currentScore);
            }

            if (beta <= alpha) {
                break;
            }
        }

        return bestMove;
    }


    private static int minimax(Ilayout board, int alpha, int beta, boolean maximizingPlayer) {
        if (board.isGameOver()) {
            return score(board);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Integer move : board.getAvailableMoves()) {
                Ilayout newBoard = (Ilayout) ((Board)board).clone();
                newBoard.move(move);
                int eval = minimax(newBoard, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Integer move : board.getAvailableMoves()) {
                Ilayout newBoard = (Ilayout) ((Board)board).clone();
                newBoard.move(move);
                int eval = minimax(newBoard, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private static int score(Ilayout board) {
        Ilayout.ID winner = board.getWinner();

        if (winner == Ilayout.ID.X) {
            return 10; // A positive score for a win for 'X'
        } else if (winner == Ilayout.ID.O) {
            return -10; // A negative score for a win for 'O'
        } else {
            return 0; // Neutral score for a draw or undecided game
        }
    }

}
