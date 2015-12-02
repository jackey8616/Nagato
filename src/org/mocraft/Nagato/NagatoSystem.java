package org.mocraft.Nagato;

import java.util.Calendar;
import java.util.TimerTask;

import org.mocraft.Nagato.TypeDefine.WebStatus;
import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class NagatoSystem extends Nagato {

	public static final String buildVersion = "b040";
	public static final String officialVersion = "Poi";
	
	private String imgCat = "img/Global/cat.png";
	private String imgTv = "img/Global/teamviewer.png";
	private String imgNonInternet = "img/Global/nonInternet.png";
	private String imgGameStart = "img/Global/gameStart.png";
	private String imgAnchor = "img/Port/anchor.png";
	private String imgPort = "img/Global/port.png";

	private String imgF5 = "img/Global/f5.png";

	public WebStatus detectWebAndFix() {
		try {
			guiMain.log("> Detecting Web Status...");
			if (imgExists(imgNonInternet)) {
				guiMain.log(">> Detected Website offline!");
				processNonInternet();
			} else if (imgExists(imgCat)) {
				guiMain.log(">> Detected Cat Error!");
				processCat();
			} else if (globalImgExists(imgTv)) {
				guiMain.log(">> Detected TeamViewer Form!");
				processTv();
			} else if (imgExists(imgGameStart)) {
				guiMain.log(">> Detected Game Start Form!");
				processGameStart();
			} else if (globalImgExists(imgAnchor) || imgExists(imgPort)) {
				guiMain.log(">> Detected Game Process Normal!");
				return WebStatus.Normal;
			} else {
				guiMain.log(">> Detected Unknown Status! Retring...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			guiMain.log(">> Unknow Error Occoured! Retry...");
		}
		return detectWebAndFix();
	}

	public void cycleDetectWebAndFix() {
		try {
			guiMain.log("Cycle Dectecting Web Status...");
			if (imgExists(imgNonInternet)) {
				guiMain.log(">> Detected Website offline!");
				processNonInternet();
			} else if (imgExists(imgCat)) {
				guiMain.log(">> Detected Cat Error!");
				processCat();
			} else if (globalImgExists(imgTv)) {
				guiMain.log(">> Detected TeamViewer Form!");
				processTv();
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			guiMain.log(">> Unknow Error Occoured! Retry...");
			cycleDetectWebAndFix();
		}
	}
	
	private void processNonInternet() throws Exception {
		guiMain.log(">>> Solving NonInternet Error...");
		while (imgExactExists(imgNonInternet)) {
			guiMain.log(">> Reflashing Web...");
			click(imgF5);
		}
		guiMain.log(">>> NonInternet Error Solved!");
	}

	private void processCat() throws Exception {
		guiMain.log(">>> Solving Cat Error...");
		while (imgExactExists(imgCat)) {
			guiMain.log(">>> Reflashing Web...");
			click(imgF5);
			if (imgExactExists(imgCat)) {
				guiMain.log(">>> Cat Error UnSolved! Retry Atfer Minute.");
				gameForm.wait(60);
			}
		}
		guiMain.log(">>> Cat Error Solved!");
	}

	private void processTv() throws Exception {
		guiMain.log(">>> Solving Tv Form...");
		while (globalImgExists(imgTv)) {
			guiMain.log(">>> Exiting Tv Form...");
			globalScreen.click("img/Global/Yes" + (imgExactExists("img/Global/Yes.png") ? "" : "-select") + ".png");
		}
		guiMain.log(">>> Tv Form Solved!");
	}

	private void processGameStart() throws Exception {
		guiMain.log(">>> Solving GameStart Form...");
		while (imgExactExists(imgGameStart)) {
			guiMain.log(">>> Entering Game...");
			click(imgGameStart);
		}
		guiMain.log(">>> GameStart Form Solved!");
	}

	public void anchorLocate() {
		try {
			guiMain.log("> Anchor Locating...");
			globalScreen.hover(imgAnchor);
			zeroPoint = new Location(Mouse.at().getX() - 775, Mouse.at().getY() - 450);
			gameForm = new Region(zeroPoint.getX(), zeroPoint.getY(), 800, 480);
			gameForm.hover(zeroPoint);
			guiMain.log("> Anchor Located!");
		} catch (Exception e) {
			e.printStackTrace();
			guiMain.log("> Unknow Error Occoured! Retry...");
			anchorLocate();
		}
	}

	public boolean detectSleep() {
		Calendar time = Calendar.getInstance();
		int now = time.get(Calendar.HOUR_OF_DAY) * 3600 + time.get(Calendar.MINUTE) * 60;

		if(guiTesk.getBeginEndTime(0) <= now && now <= 86340) {
			return true;
		} else if(0 <= now && now <= guiTesk.getBeginEndTime(1)) { 
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Override Sikuli Method.
	 * 
	 */
	public void click(String path) throws Exception {
		if (imgExists(path)) {
			gameForm.click(path);
		}
	}

	public boolean globalImgExists(String path) {
		return (globalScreen.exists(path) == null ? false : true);
	}
	
	public boolean imgExactExists(String path) {
		return (gameForm.exists(new Pattern(path).exact()) == null ? false : true);
	}

	public boolean imgExists(String path) {
		return (gameForm.exists(path) == null ? false : true);
	}

	public boolean imgExists(String path, double value) {
		return (gameForm.exists(path, value) == null ? false : true);
	}

	class MainThread extends TimerTask {	
		
		@Override
		public void run() {
			if (guiTesk.getRadioSwitch() && system.detectSleep()) {
				guiMain.log("Sleeping...");
			} else {
				system.cycleDetectWebAndFix();
				port.detectFlagAndProcess();
				surply.detectNeedAndSurply();
				port.detectFlagAndProcess();
				attack.detectTargetAndSendLevy();
				if (team.hasTrapLeving()) {
					gameForm.wait(60.0);
				}
			}
		}
	}
}
