package museumheistsd.sharedregions;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.ILogger;
import museumheistsd.interfaces.IMuseum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Pedro1
 */
public class SharedLogger implements ILogger {

    FileOutputStream log;

    Thief[] thieves;
    int mtStatus;
    int nThieves;
    int nAssaultParties;
    int elemsPerParty;

    AssaultParty assaultParties[];
    MasterThief mThief;

    IMuseum museum;

    private SharedLogger(int nThieves, int nAssaultParties, int elemsPerParty, IMuseum museum) {
        this.nThieves = nThieves;
        this.nAssaultParties = nAssaultParties;
        this.elemsPerParty = elemsPerParty;
        thieves = new Thief[nThieves];
        this.museum = museum;
        this.assaultParties = new AssaultParty[nAssaultParties];
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
        SharedLogger l = new SharedLogger(nThieves,nAssaultParties,elemsPerParty,museum);
        l.log();
        return l;
    }


    @Override
    public void beginLog() throws Exception {

    }

    @Override
    public void setStatusThief(Thread t) throws Exception {
        long id = t.getId();
        this.thieves[(int) id] = (Thief) t;
        this.log();
    }

    @Override
    public void setStatusMtThief(Thread t) throws Exception {
        this.mThief = (MasterThief) t;
        this.log();
    }

    @Override
    public void setAssaultParty(AssaultParty a) throws Exception {
        assaultParties[a.getID()] = a;
        this.log();
    }

    @Override
    public synchronized void log() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("MstT");
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


        for (int i = 1; i < museum.getRooms().length + 1; i++) {
            s.append("Room ").append(i);
        }

        for (int i = 1; i < nAssaultParties + 1; i++) {
            s.append("RId");
            for (int j = 1; i < elemsPerParty + 1; i++) {
                s.append(" Id Pos CV").append(j).append("\t");
            }
            s.append("\t");
        }

        s.append("NP  DT".repeat(museum.getRooms().length));

        s.append("\n").append(String.format("%4d", mThief.getStatus()));
        for (int i = 1; i < nThieves + 1; i++) {
            s.append(" ").append(String.format("%4d  %s %d", thieves[i - 1].getStatus(), this.thieves[i - 1].getSituation(), this.thieves[i - 1].getMD())).append(i);
        }


        for (int i = 1; i < nAssaultParties + 1; i++) {
            s.append(String.format("%d\t", assaultParties[i - 1].getRoomId()));
            for (int j = 1; i < elemsPerParty + 1; i++) {
                s.append(String.format(" %d %2d %d", thieves[j - 1].getId(), assaultParties[i - 1].getThiefPos(j), assaultParties[i - 1].getThiefHasCanvas(j - 1))).append(j).append("  ");
            }
            s.append("\t");
        }


        Arrays.stream(museum.getRooms()).forEach(room -> {
            s.append(String.format("%2d %2d", room.getCanvasLeft(), room.getDistance()));
        });

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
