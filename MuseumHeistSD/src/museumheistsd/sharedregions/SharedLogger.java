package museumheistsd.sharedregions;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.ILogger;
import museumheistsd.interfaces.IMuseum;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Pedro1
 */
public class SharedLogger implements ILogger {

    FileOutputStream log;

    int[] thievesId;
    int[] thievesStatus;
    int[] thievesPartyId;
    boolean[] thievesHasCanvas;
    int[] thievesMD;
    int[] thievesPartyIdPos;
    int[] thievesSituation;

    int[][] partyThievesId;
    int mtStatus;
    int nThieves;
    int nAssaultParties;
    int elemsPerParty;

    int nRooms;

    List<HashMap<String, Integer>> assaultParties;
    MasterThief mThief;

    int[] roomsDistance;
    int[] roomsCanvasLeft;

    private SharedLogger(int nThieves, int nAssaultParties, int elemsPerParty, IMuseum museum) {
        this.nThieves = nThieves;
        this.nAssaultParties = nAssaultParties;
        this.elemsPerParty = elemsPerParty;
        thievesPartyId = new int[nThieves];
        thievesPartyIdPos = new int[nThieves];
        thievesStatus = new int[nThieves];
        thievesMD = new int[nThieves];
        partyThievesId = new int[nAssaultParties][elemsPerParty];
        this.nRooms = 10;
        roomsDistance = new int[nRooms];
        roomsCanvasLeft = new int[nRooms];
        Path filePath = Path.of("log.txt");
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            log = new FileOutputStream(filePath.toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static SharedLogger createLogger(int nThieves, int nAssaultParties, int elemsPerParty, IMuseum museum) throws Exception {
        return new SharedLogger(nThieves, nAssaultParties, elemsPerParty, museum);
    }


    @Override
    public void beginLog() throws Exception {

        StringBuilder s = new StringBuilder();
        s.append("MstT\t");
        for (int i = 1; i < nThieves + 1; i++) {
            s.append(" ").append("Thief ").append(i);
        }
        s.append("\n").append("Stat  ");

        s.append("Stat S MD\t".repeat(Math.max(0, nThieves)));
        s.append("\n\t\t");

        for (int i = 1; i < nAssaultParties + 1; i++) {
            s.append("Assault Party ").append(i).append("\t");
        }
        for (int i = 1; i < nAssaultParties + 1; i++) {
            for (int j = 1; i < elemsPerParty + 1; i++) {
                s.append("Elem ").append(j).append("\t");
            }
            s.append("\t");
        }


        for (int i = 1; i < nRooms + 1; i++) {
            s.append("Room ").append(i);
        }

        for (int i = 1; i < nAssaultParties + 1; i++) {
            s.append("RId");
            for (int j = 1; i < elemsPerParty + 1; i++) {
                s.append(" Id Pos CV").append(j).append("\t");
            }
            s.append("\t");
        }

        s.append("NP  DT".repeat(nRooms));

        try {
            log.write(s.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setStatusThief(Thief t) throws Exception {
        int id = (int) t.getId();
        Thief tt = (Thief) t;
        this.thievesHasCanvas[id] = ((Thief) t).getHasCanvas();
        this.thievesStatus[id] = tt.getStatus();
        this.thievesPartyId[id] = tt.getPartyID();
        this.thievesPartyIdPos[id] = tt.getPartyPos();
        this.assaultParties.get(tt.getPartyID()).put("ElemId", (int) tt.getId());
        this.assaultParties.get(tt.getPartyID()).put("ElemPos", tt.getPartyPos());
        this.assaultParties.get(tt.getPartyID()).put("ElemCV", tt.getHasCanvas() ? 1 : 0);
        this.log();
    }

    @Override
    public void setStatusMtThief(Thread t) throws Exception {
        MasterThief mThief1 = (MasterThief) t;
        this.mtStatus = mThief1.getStatus();
        this.log();
    }

    @Override
    public void setAssaultParty(AssaultParty a) throws Exception {
        assaultParties.get(a.getID()).put("Rid", a.getRoomId());
    }

    @Override
    public synchronized void log() throws Exception {
        StringBuilder s = new StringBuilder();

        s.append("\n").append(String.format("%4d", mThief.getStatus()));
        for (int i = 1; i < nThieves + 1; i++) {
            s.append(" ").append(String.format("%4d  %s %d", thievesStatus[i - 1], this.thievesPartyId[i - 1] == -1 ? "W" : "P", this.thievesMD[i - 1])).append(i);
        }


        for (int i = 1; i < nAssaultParties + 1; i++) {
            s.append(String.format("%d\t", assaultParties.get(i - 1).get("Rid")));

            for (int j = 1; i < elemsPerParty + 1; i++) {
                s.append(String.format(" %d %2d %d", thievesId[j - 1], thievesPartyIdPos[i - 1], thievesHasCanvas[i - 1] ? 1 : 0)).append(j).append("  ");
            }
            s.append("\t");
        }

        for (int i = 0; i < nRooms; i++) {
            s.append(String.format(" %2d %2d ", roomsCanvasLeft[i], roomsDistance[i]));

        }

        try {
            log.write(s.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void logResults() {
        try {
            log.write(String.format("My friends, tonight's effort produced %2d priceless paintings!", this.mThief.getPaintingsStolen()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
;