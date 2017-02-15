package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Test;

import com.teamtreehouse.techdegree.overboard.model.Board;
import com.teamtreehouse.techdegree.overboard.model.Question;
import com.teamtreehouse.techdegree.overboard.model.User;

import static org.junit.Assert.assertEquals;


public class UserTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
    }

    @Test
    public void usersReputationIncreasesWhenQuestionIsUpvoted() throws Exception {
        // Arrange
        User me = new User(board, "me");
        User anotherUser = new User(board, "another user");
        Question question = me.askQuestion("What is your favorite programming language?");

        // Act (method being tested is the User.upVote() method)
        anotherUser.upVote(question);
        int reputation = me.getReputation();

        // Assert
        assertEquals(5, reputation);
    }
}