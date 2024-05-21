package gui;

import rank.WebExtended;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;


public class CtrRank implements ActionListener {
    private ViewRank vRank;
    private WebExtended web;

    public CtrRank(ViewRank vr) {
        vRank = vr;
    }

    public void actionPerformed(ActionEvent event) {
        try {
			ViewRank.Command command = ViewRank.Command.valueOf(event.getActionCommand());
            switch (command) {
			case CREATE:	this.create();	break;
			case CLICK:		this.click();	break;
			case BYNAME:	this.byname();	break;
			case BYRANK:	this.byrank();	break;
			case SIMULATE:	this.simulate();break;
			case SWITCH:	this.switchop();break;
			}
        } catch (IllegalArgumentException | NoSuchElementException e) {
            vRank.setError(e.getMessage());
        } catch (NullPointerException e) {
            vRank.setError("Web not created");
        } catch (IOException e) {
            vRank.setError("File not found");
        } catch (Exception e) {
            vRank.setError("Error: "+e.getMessage());
        }
    }
	//------------------------------------------------------------------
    private void create() throws IOException {
		web = new WebExtended();
		String fn = vRank.getFileName();
		createLinks(fn);
		vRank.addOutputLine("Create web");
		vRank.addOutputLine(web.toString());
	}
    private void createLinks(String fn) throws IOException {
		try (BufferedReader buffReader = Files.newBufferedReader(Path.of(fn))) {
			String dataLink = buffReader.readLine();
			while (dataLink != null) {
				web.addLink(dataLink);
				dataLink = buffReader.readLine();
			}
		}
    }
    private void click() {
		String name = vRank.getSiteName();
		web.click(name);
		vRank.addOutputLine("Click in " + web.getSite(name));
	}
    private void byname() {
		vRank.addOutputLine("Sites by Names");
		vRank.addOutputLine(web.getSitesByName().toString());
	}
    private void byrank() {
		vRank.addOutputLine("Sites by Rank");
		vRank.addOutputLine(web.getSitesByRank().toString());
	}
    private void simulate() {
		web.simulateClick(1000);
		vRank.addOutputLine("Simulate 1000 click");
	}
    private void switchop() {
		String name = vRank.getSiteName();
		web.switchSiteWithName(name);
		vRank.addOutputLine("Switch " + web.getSite(name));
	}
}
