package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class UserTest {

    private Answer answer;
    private Board board;
    private User me;
    private User anotherUser;
    private Question question;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        // Act
        anotherUser.upVote(question);

        // Assert
        assertEquals(5, me.getReputation());
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsUpvoted() throws Exception {
        me.upVote(answer);

        assertEquals(10, anotherUser.getReputation());
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsAccepted() throws Exception {
        me.acceptAnswer(answer);

        assertEquals(15, anotherUser.getReputation());
    }

    @Test(expected = VotingException.class)
    public void upvotingUsersOwnQuestionThrowsException() throws Exception {
        me.upVote(question);
    }

    @Test(expected = VotingException.class)
    public void downvotingUsersOwnQuestionThrowsException() throws Exception {
        me.downVote(question);
    }

    @Test
    public void userOtherThanQuestionerCannotAcceptAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only me can accept this answer as it is their question");

        anotherUser.acceptAnswer(answer);
    }

    @Test
    public void questionerCanAcceptAnswer() throws Exception {
        me.acceptAnswer(answer);

        assertTrue(answer.isAccepted());
    }

    @Test
    public void userReputationDecreasesWhenAnswerIsDownvoted() throws Exception {
        me.downVote(answer);

        assertEquals(-1, anotherUser.getReputation());
    }

    @Test
    public void userReputationDecreasesWhenQuestionIsDownvoted() throws Exception {
        anotherUser.downVote(question);

        assertEquals(-1, me.getReputation());
    }
}