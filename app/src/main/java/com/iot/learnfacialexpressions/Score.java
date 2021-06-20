package com.iot.learnfacialexpressions;

public class Score {

        public String score;
        public String date;

        public Score() {
        }

        public Score(String score, String date) {
            this.score = score;
            this.date = date;
        }

        public String getScore(){
            return this.score;
        }

        public String getDate(){
            return this.date;
        }

        public int getScoreImage(){
            int scoreInteger = getScoreInteger();
            if(scoreInteger < 5){
              return R.drawable.thumbs_down;
            }else{
                return R.drawable.thumbsup;
            }
        }

        public int getScoreInteger(){
          return Integer.parseInt(this.score.substring(0, this.score.indexOf('/')));
        }

}
