package posylka;

/** Mediator: єдина точка, через яку пристрої повідомляють про події. */
public interface SortingMediator {
    void onEvent(SortingDevice sender, SortingEvent event, Parcel parcel, String detail);
}
