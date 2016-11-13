package com.counterbell.agent.common.utils;

import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 13/11/16.
 */
public class IOUtils {

    public static long copyLarge(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copyLarge(InputStream in, SnapshotWriter out) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copyLarge(SnapshotReader in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;
        while (in.hasRemaining()) {
            long n = in.remaining();
            in.read(buffer);
            long read = n - in.remaining();
            out.write(buffer,0,(int)read);
            count += read;
        }
        return count;
    }
}
