package edu.java.scrapper.processor;

import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.StackOverflowService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowProcessor extends Processor {
    private final StackOverflowClient client;
    private final StackOverflowService stackOverflowService;

    protected StackOverflowProcessor(
        StackOverflowClient client,
        LinkService jooqLinkService,
        StackOverflowService jooqStackOverflowService
    ) {
        super("stackoverflow.com", jooqLinkService);
        this.client = client;
        this.stackOverflowService = jooqStackOverflowService;
    }

    @Override
    public Mono<String> process(Link link) {
        URI uri = URI.create(link.getUri());
        Long id = extract(uri.getPath());
        Optional<StackOverflowLink> previous = stackOverflowService.findLink(link);
        return client.getQuestionInfo(id)
            .map(resp -> resp.items().getFirst())
            .mapNotNull(resp -> {
                StringBuilder message = new StringBuilder();
                if (resp.lastActivityDate().isAfter(OffsetDateTime.from(link.getLastUpdatedAt()))) {
                    link.setLastUpdatedAt(resp.lastActivityDate());
                    service().updateLink(link);
                    message.append("Question has recent updates");
                }

                boolean isDirty = false;
                StackOverflowLink stackOverflowLink = getEntity(previous, link);

                if (previous.isEmpty() || !stackOverflowLink.getAnswerCount().equals(resp.answerCount())) {
                    isDirty = true;
                    if (previous.isPresent()) {
                        message.append("Количество ответов изменилось: %d -> %d\n"
                            .formatted(stackOverflowLink.getAnswerCount(), resp.answerCount()));
                    }
                    stackOverflowLink.setAnswerCount(resp.answerCount());
                }

                if (previous.isEmpty() || !stackOverflowLink.getScore().equals(resp.score())) {
                    isDirty = true;
                    if (previous.isPresent()) {
                        message.append("Количество очков изменилось: %d -> %d\n"
                            .formatted(stackOverflowLink.getScore(), resp.score()));
                    }
                    stackOverflowLink.setScore(resp.score());
                }

                if (isDirty) {
                    updateEntity(stackOverflowLink, previous.isEmpty());
                }

                return message.isEmpty() ? null : message.toString();
            });
    }

    private Long extract(String path) {
        Pattern pattern = Pattern.compile("/questions/(\\d+)");
        Matcher matcher = pattern.matcher(path);
        return Long.valueOf(matcher.group(1));
    }

    private StackOverflowLink getEntity(Optional<StackOverflowLink> repositoryLink, Link link) {
        if (repositoryLink.isPresent()) {
            return repositoryLink.get();
        }

        StackOverflowLink stackOverflowLink = new StackOverflowLink();
        stackOverflowLink.setId(link.getId());

        return stackOverflowLink;
    }

    private void updateEntity(StackOverflowLink stackOverflowLink, boolean isNewEntity) {
        if (isNewEntity) {
            stackOverflowService.save(stackOverflowLink);
        } else {
            stackOverflowService.updateLink(stackOverflowLink);
        }
    }
}
