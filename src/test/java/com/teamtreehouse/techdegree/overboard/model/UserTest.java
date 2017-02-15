package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class UserTest {

    private Board board;
    private User me;
    private User anotherUser;
    private Question question;

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        me = new User(board, "me");
        anotherUser = new User(board, "another user");
        question = me.askQuestion("What is your favorite programming language?");
    }

    @Test
    public void userReputationIncreasesWhenQuestionIsUpvoted() throws Exception {
        // Arrange

        // Act (method being tested is the User.upVote() method)
        anotherUser.upVote(question);
        int reputation = me.getReputation();

        // Assert
        assertEquals(5, reputation);
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsUpvoted() {
        // Arrange
        Answer answer = anotherUser.answerQuestion(question, "Python");

        // check if answerer's reputation goes up
        me.upVote(answer);
        int reputation = anotherUser.getReputation();

        // Assert
        assertEquals(10, reputation);
    }
}