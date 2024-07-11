package org.example.model;

public class Score {
    private int score;

    private Score() {}

    private Score(Builder builder) {
        score = builder.score;
    }

    public int getScore() {
        return score;
    }

    public static final class Builder {
        private int score;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder score(int val) {
            score = val;
            return this;
        }

        public Score build() {
            return new Score(this);
        }
    }
}
