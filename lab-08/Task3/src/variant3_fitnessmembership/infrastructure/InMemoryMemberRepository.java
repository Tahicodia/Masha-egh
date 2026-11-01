package variant3_fitnessmembership.infrastructure;

import variant3_fitnessmembership.application.MemberRepository;
import variant3_fitnessmembership.domain.Member;

import java.util.HashMap;
import java.util.Map;

/** Заглушка сховища клієнтів: відтворює тестові дані з Legacy-коду (телефон, дисконтна картка). */
public final class InMemoryMemberRepository implements MemberRepository {
    private static final String DEFAULT_PHONE = "+380509998877";

    private final Map<String, Member> byName = new HashMap<>();
    private int nextId = 42;

    @Override
    public Member findOrRegister(String name) {
        return byName.computeIfAbsent(name, n -> new Member(nextId++, n, DEFAULT_PHONE, true));
    }
}
