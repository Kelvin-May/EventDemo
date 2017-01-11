package com.yjk.eventdemo.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class StorageUtil {
    private static String otherExternalStorageDirectory = null;
    private static int kOtherExternalStorageStateUnknow = -1;
    private static int kOtherExternalStorageStateUnable = 0;
    private static int kOtherExternalStorageStateIdle = 1;
    private static int otherExternalStorageState = kOtherExternalStorageStateUnknow;
    private static String internalStorageDirectory;

    public static Context context;

    public static void init(Context cxt) {
        context = cxt;
    }

    /**
     * get external Storage available space
     */
    public static long getExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs statfs = new StatFs(path.getPath());

        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statfs.getBlockSizeLong();
        } else {
            blockSize = statfs.getBlockSize();
        }

        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statfs.getAvailableBlocksLong();
        } else {
            availableBlocks = statfs.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }

    public final static String getInternalStorageDirectory() {
        if (TextUtils.isEmpty(internalStorageDirectory)) {
            File file = context.getFilesDir();
            internalStorageDirectory = file.getAbsolutePath();
            if (!file.exists())
                file.mkdirs();
            String shellScript = "chmod 705 " + internalStorageDirectory;
            runShellScriptForWait(shellScript);
        }
        return internalStorageDirectory;
    }

    public static long getInternalStorageAvailableSpace() {
        String path = getInternalStorageDirectory();
        StatFs stat = new StatFs(path);
//      long blockSize = stat.getBlockSizeLong();
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
//      long availableBlocks = stat.getAvailableBlocksLong();
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }

        return blockSize * availableBlocks;
    }


    public final static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory() + File.separator + "";
    }

    /**
     * get sdcard2 external Storage available space
     */
    public static long getSdcard2StorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        String path = getSdcard2StorageDirectory();
        File file = new File(path);
        if (!file.exists())
            return 0;
        StatFs statfs = new StatFs(path);
//      long blockSize = statfs.getBlockSizeLong();
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statfs.getBlockSizeLong();
        } else {
            blockSize = statfs.getBlockSize();
        }

//      long availableBlocks = statfs.getAvailableBlocksLong();
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statfs.getAvailableBlocksLong();
        } else {
            availableBlocks = statfs.getAvailableBlocks();
        }

        return blockSize * availableBlocks;
    }

    public final static String getSdcard2StorageDirectory() {
        return "/mnt/sdcard2/";
    }

    public static boolean runShellScriptForWait(final String cmd) throws SecurityException {
        ShellThread thread = new ShellThread(cmd);
        thread.setDaemon(true);
        thread.start();
        int k = 0;
        while (!thread.isReturn() && k++ < 20) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (k >= 20) {
            thread.interrupt();
        }
        return thread.isSuccess();
    }

    /**
     * 用于执行shell脚本的线程
     */
    private static class ShellThread extends Thread {
        private boolean isReturn;
        private boolean isSuccess;
        private String cmd;

        public boolean isReturn() {
            return isReturn;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public ShellThread(String cmd) {
            this.cmd = cmd;
        }

        @Override
        public void run() {
            try {
                Runtime runtime = Runtime.getRuntime();
                Process proc;
                try {
                    proc = runtime.exec(cmd);
                    isSuccess = (proc.waitFor() == 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isSuccess = true;
            } catch (InterruptedException e) {
            }
            isReturn = true;
        }
    }

    /**
     * get EMMC internal Storage available space
     */
    public static long getEmmcStorageAvailableSpace() {
        String path = getEmmcStorageDirectory();
        File file = new File(path);
        if (!file.exists())
            return 0;
        StatFs statfs = new StatFs(path);
//      long blockSize = statfs.getBlockSizeLong();
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statfs.getBlockSizeLong();
        } else {
            blockSize = statfs.getBlockSize();
        }

//      long availableBlocks = statfs.getAvailableBlocksLong();
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statfs.getAvailableBlocksLong();
        } else {
            availableBlocks = statfs.getAvailableBlocks();
        }

        return blockSize * availableBlocks;
    }

    public final static String getEmmcStorageDirectory() {
        return "/mnt/emmc/";
    }

    /**
     * get other external Storage available space
     */
    public static long getOtherExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        if (otherExternalStorageState == kOtherExternalStorageStateUnable)
            return 0;
        if (otherExternalStorageDirectory == null) {
            getOtherExternalStorageDirectory();
        }
        if (otherExternalStorageDirectory == null)
            return 0;
        StatFs statfs = new StatFs(otherExternalStorageDirectory);
//      long blockSize = statfs.getBlockSizeLong();
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statfs.getBlockSizeLong();
        } else {
            blockSize = statfs.getBlockSize();
        }
//      long availableBlocks = statfs.getAvailableBlocksLong();
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statfs.getAvailableBlocksLong();
        } else {
            availableBlocks = statfs.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }


    public static String getOtherExternalStorageDirectory() {
        if (otherExternalStorageState == kOtherExternalStorageStateUnable)
            return null;
        if (otherExternalStorageState == kOtherExternalStorageStateUnknow) {
            FstabReader fsReader = new FstabReader();
            if (fsReader.size() <= 0) {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
                return null;
            }
            List<StorageInfo> storages = fsReader.getStorages();
            /* 对于可用空间小于100M的挂载节点忽略掉 */
            long availableSpace = 100 << (20);
            String path = null;
            for (int i = 0; i < storages.size(); i++) {
                StorageInfo info = storages.get(i);
                if (info.getAvailableSpace() > availableSpace) {
                    availableSpace = info.getAvailableSpace();
                    path = info.getPath();
                }
            }
            otherExternalStorageDirectory = path;
            if (otherExternalStorageDirectory != null) {
                otherExternalStorageState = kOtherExternalStorageStateIdle;
            } else {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
            }
            if (!TextUtils.isEmpty(otherExternalStorageDirectory)) {
                if (!otherExternalStorageDirectory.endsWith("/")) {
                    otherExternalStorageDirectory = otherExternalStorageDirectory + "/";
                }
            }
        }
        return otherExternalStorageDirectory;
    }

    public static class FstabReader {
        public FstabReader() {
            init();
        }

        public int size() {
            return storages == null ? 0 : storages.size();
        }

        public List<StorageInfo> getStorages() {
            return storages;
        }

        final List<StorageInfo> storages = new ArrayList<StorageInfo>();

        public void init() {
            File file = new File("/system/etc/vold.fstab");
            if (file.exists()) {
                FileReader fr = null;
                BufferedReader br = null;
                try {
                    fr = new FileReader(file);
                    if (fr != null) {
                        br = new BufferedReader(fr);
                        String s = br.readLine();
                        while (s != null) {
                            if (s.startsWith("dev_mount")) {
                                /* "\s"转义符匹配的内容有：半/全角空格 */
                                String[] tokens = s.split("\\s");
                                String path = tokens[2]; // mount_point
                                StatFs stat = new StatFs(path);

                                long blockSize;
                                long totalBlocks;
                                long availableBlocks;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    blockSize = stat.getBlockSizeLong();
                                } else {
                                    blockSize = stat.getBlockSize();
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    totalBlocks = stat.getBlockCountLong();
                                } else {
                                    totalBlocks = stat.getBlockCount();
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    availableBlocks = stat.getAvailableBlocksLong();
                                } else {
                                    availableBlocks = stat.getAvailableBlocks();
                                }

//                              if (null != stat&& stat.getAvailableBlocksLong() > 0) {
//
//                                  long availableSpace = stat.getAvailableBlocksLong()* stat.getBlockSizeLong();
//                                  long totalSpace = stat.getBlockCountLong()* stat.getBlockSizeLong();
                                if (null != stat && availableBlocks > 0) {

                                    long availableSpace = availableBlocks * blockSize;
                                    long totalSpace = totalBlocks * blockSize;
                                    StorageInfo storage = new StorageInfo(path,
                                            availableSpace, totalSpace);
                                    storages.add(storage);
                                }
                            }
                            s = br.readLine();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fr != null)
                        try {
                            fr.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    if (br != null)
                        try {
                            br.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    static class StorageInfo implements Comparable<StorageInfo> {
        private String path;
        private long availableSpace;
        private long totalSpace;

        StorageInfo(String path, long availableSpace, long totalSpace) {
            this.path = path;
            this.availableSpace = availableSpace;
            this.totalSpace = totalSpace;
        }

        @Override
        public int compareTo(StorageInfo another) {
            if (null == another)
                return 1;

            return this.totalSpace - another.totalSpace > 0 ? 1 : -1;
        }

        long getAvailableSpace() {
            return availableSpace;
        }

        long getTotalSpace() {
            return totalSpace;
        }

        String getPath() {
            return path;
        }
    }

}
