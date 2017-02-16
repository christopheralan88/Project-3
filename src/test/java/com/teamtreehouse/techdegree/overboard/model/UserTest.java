package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserTest {

    private Answer answer;
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
        answer = anotherUser.answerQuestion(question, "Python");
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
        // check if answerer's reputation goes up
        me.upVote(answer);
        int reputation = anotherUser.getReputation();

        // Assert
        assertEquals(10, reputation);
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsAccepted() {
        me.acceptAnswer(answer);
        int reputation = anotherUser.getReputation();

        assertEquals(15, reputation);
    }

    @Test(expected = VotingException.class)
    public void upvotingUsersOwnQuestionThrowsException() {
        me.upVote(question);
    }

    @Test(expected = VotingException.class)
    public void downvotingUsersOwnQuestionThrowsException() {
        me.downVote(question);
    }

    @Test(expected = AnswerAcceptanceException.class)
    public void userOtherThanQuestionerCannotAcceptAnswer() {
        // Act
        anotherUser.acceptAnswer(answer);
        //TODO:  check that exception message is returned correctly
    }

    @Test
    public void questionerCanAcceptAnswer() {
        // Act
        me.acceptAnswer(answer);

        // Assert
        assertTrue(answer.isAccepted());
    }

    @Test
    public void userReputationDecreasesWhenQuestionIsDownvoted() {
        // Act
        anotherUser.downVote(question);
        int reputation = me.getReputation();

        // Assert
        assertEquals(-1, reputation);
    }
}