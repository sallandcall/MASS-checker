package eu.qped.java.checkers.syntax.feedback.fromatter;

import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.hint.Hint;
import eu.qped.framework.feedback.hint.HintType;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.code.CodeBlock;
import net.steppschuh.markdowngenerator.text.heading.Heading;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownFeedbackFormatter implements IFeedbackFormatter {


    @Override
    public List<Feedback> format(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::formatInMarkdown).collect(Collectors.toList());
    }

    private Feedback formatInMarkdown(Feedback feedback) {

        Feedback formatted = Feedback.builder().build();
        var formattedTitle = String.valueOf(new Heading(feedback.getTitle().trim(), 4));
        formatted.setTitle(formattedTitle);
        List<Hint> formattedHints = formatHints(feedback.getHints());
        formatted.setHints(formattedHints);
        formatted.setErrorCause(feedback.getErrorCause());
        formatted.setSeverity(feedback.getSeverity());
        formatted.setErrorLocation(feedback.getErrorLocation());

        return formatted;
    }

    private List<Hint> formatHints(List<Hint> hints) {
        List<Hint> formattedHints = new ArrayList<>();
        hints.forEach(
                h -> {
                    var tempHint = Hint.builder().build();
                    if (h.getType() == HintType.TEXT) {
                        tempHint.setContent(String.valueOf(new Text(h.getContent())));
                    } else if (h.getType() == HintType.CODE_EXAMPLE) {
                        tempHint.setContent(
                                String.valueOf(new CodeBlock(h.getContent(), "java"))
                        );
                    }
                    formattedHints.add(tempHint);
                }
        );
        return formattedHints;
    }
}
