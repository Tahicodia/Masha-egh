package variant3_fitnessmembership.infrastructure;
//** 	Зберігає та шукає клієнтів у пам'яті програми*/
import java.util.HashMap;
import java.util.Map;
import variant3_fitnessmembership.application.MemberRepository;
import variant3_fitnessmembership.domain.Member;

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
