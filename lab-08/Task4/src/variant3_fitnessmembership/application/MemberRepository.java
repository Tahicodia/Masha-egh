package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.Member;

public interface MemberRepository {
    Member findOrRegister(String name);
}
