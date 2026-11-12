public class PartnerTrackingService implements TrackingService {
    private int paidRequests = 0;

    public String getStatus(String waybill){
        paidRequests++;
        Logger.log("PartnerTrackingService", "Платний запит до партнера (" + waybill + ")");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        return "У дорозі";
    }

    public int getPaidRequests(){
        return paidRequests;
    }
}
