package even.rrsquiz;

import even.rrsquiz.animation.Animation;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author even
 */
public class Problem
{

    private File file;
    private String intro;
    private String question;
    private Animation animation;
    private ArrayList<Response> responses;
    private String comment;

    /**
     * The parser is expected to build the problem object, so no initialization
     * is done here
     */
    public Problem() {
        responses = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Animation getAnimation() {
        return animation;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Response> getResponses() {
        return responses;
    }

    public void addResponse(Response response) {
        responses.add(response);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
