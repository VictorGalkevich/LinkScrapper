package edu.java.scrapper.processor;

import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.dto.StackOverflowResponseDto;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import reactor.core.publisher.Mono;

public class StackOverflowProcessor extends Processor {
    private final StackOverflowClient client;

    protected StackOverflowProcessor(StackOverflowClient client, LinkService service) {
        super("stackoverflow.com", service);
        this.client = client;
    }

    @Override
    public Mono<String> process(Link link) {
        URI uri = URI.create(link.getUri());
        Long id = extract(uri.getPath());
        return client.getQuestionInfo(id)
                .mapNotNull(resp -> {
                            StackOverflowResponseDto.Question question = resp.items().getFirst();
                            if (question.lastActivityDate().isAfter(OffsetDateTime.from(link.getLastUpdatedAt()))) {
                                link.setLastUpdatedAt(question.lastActivityDate());
                                service().updateLink(link);
                                return "Question has recent updates";
                            }
                            return null;
                        }
                );
    }

    private Long extract(String path) {
        Pattern pattern = Pattern.compile("/questions/(\\d+)");
        Matcher matcher = pattern.matcher(path);
        return Long.valueOf(matcher.group(1));
    }
}
