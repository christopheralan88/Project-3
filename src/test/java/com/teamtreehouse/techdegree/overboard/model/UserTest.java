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
    private User questioner;
    private User answerer;
    private Question question;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        questioner = new User(board, "questioner");
        answerer = new User(board, "answerer");
        question = questioner.askQuestion("What is your favorite programming language?");
        answer = answerer.answerQuestion(question, "Python");
    }

    @Test
    public void userReputationIncreasesWhenQuestionIsUpvoted() throws Exception {
        // Act
        answerer.upVote(question);

        // Assert
        assertEquals(5, questioner.getReputation());
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsUpvoted() throws Exception {
        questioner.upVote(answer);

        assertEquals(10, answerer.getReputation());
    }

    @Test
    public void userReputationIncreasesWhenAnswerIsAccepted() throws Exception {
        questioner.acceptAnswer(answer);

        assertEquals(15, answerer.getReputation());
    }

    @Test(expected = VotingException.class)
    public void upvotingUsersOwnQuestionThrowsException() throws Exception {
        questioner.upVote(question);
    }

    @Test(expected = VotingException.class)
    public void downvotingUsersOwnQuestionThrowsException() throws Exception {
        questioner.downVote(question);
    }

    @Test(expected = VotingException.class)
    public void upvotingUsersOwnAnswerThrowsException() throws Exception {
        answerer.upVote(answer);
    }

    @Test(expected = VotingException.class)
    public void downvotingUsersOwnAnswerThrowsException() throws Exception {
        answerer.downVote(answer);
    }

    @Test
    public void userOtherThanQuestionerCannotAcceptAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only questioner can accept this answer as it is their question");

        answerer.acceptAnswer(answer);
    }

    @Test
    public void questionerCanAcceptAnswer() throws Exception {
        questioner.acceptAnswer(answer);

        assertTrue(answer.isAccepted());
    }

    @Test
    public void userReputationDecreasesWhenAnswerIsDownvoted() throws Exception {
        questioner.downVote(answer);

        assertEquals(-1, answerer.getReputation());
    }

    @Test
    public void userReputationDecreasesWhenQuestionIsDownvoted() throws Exception {
        answerer.downVote(question);

        assertEquals(-1, questioner.getReputation());
    }
}