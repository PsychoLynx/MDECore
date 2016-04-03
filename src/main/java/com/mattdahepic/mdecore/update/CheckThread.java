package com.mattdahepic.mdecore.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class CheckThread extends Thread {
    private String modid;
    private String remoteUrl;
    public CheckThread(String modid, String remoteUrl) {
        setName("MattDahEpic Version Checker Thread - " + modid);
        setDaemon(true);
        this.modid = modid;
        this.remoteUrl = remoteUrl;
        start();
    }
    public void run () {
        String remoteVersion;
        try {
            URL updateUrl = new URL(remoteUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(updateUrl.openStream()));
            remoteVersion = reader.readLine();
            reader.close();
        } catch (Exception e) {
            System.err.println("Error during attempted update check: "+e.toString());
            remoteVersion = null;
        }
        UpdateChecker.remoteVersions.put(modid, remoteVersion);
    }
}