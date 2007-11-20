// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thash.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * DOC slanglois class global comment. Detailled comment
 */
class NewMultiplePointerSimpleHashFile implements IMapHashFile {

    private static NewMultiplePointerSimpleHashFile instance;

    private NewMultiplePointerSimpleHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized NewMultiplePointerSimpleHashFile getInstance() {
        if (instance == null) {
            instance = new NewMultiplePointerSimpleHashFile();
        }
        return instance;
    }

    RandomAccessFile bw = null;

    boolean readonly;

    static final int START_POSITION = 0;

    long position;

    RandomAccessFile ra = null;

    private FileOutputStream fis;

    Object lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    private int count;

    boolean threaded = true;

    long totalGetTime = 0;

    private ReadPointerCollection oraArray = null;

    long readCounter = 0;

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            long timeBefore = System.currentTimeMillis();
            // System.out.println("Require: " + cursorPosition);
            ObservableRandomAccessFile ora = oraArray.getPointer(cursorPosition);
            byte[] byteArray = ora.read(cursorPosition);
            readCounter++;
            if (readCounter == 1000) {
                oraArray.scatterPointers();
                readCounter = 0;
            }
            totalGetTime += System.currentTimeMillis() - timeBefore;
            lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
            lastRetrievedCursorPosition = cursorPosition;
            // if ((++count + 1) % 10000 == 0) {
            // System.out.println("totalGetTime from disk=" + totalGetTime + " ms");
            // }
        }
        // System.out.println("After: " + oraArray);

        return lastRetrievedObject;
    }

    public long put(String container, Object bean) throws IOException {

        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(bean);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int sizeBytes = byteArrayOutputStream.size();

        if (!readonly) {
            bw.writeInt(sizeBytes);
            bw.write(byteArrayOutputStream.toByteArray());
        }

        byteArrayOutputStream.close();

        long returnPosition = position;

        position += (4 + sizeBytes);

        return returnPosition;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container);
            file.delete();
            position = START_POSITION;
            bw = new RandomAccessFile(container, "rw");
        }
    }

    public void endPut() throws IOException {
        if (!readonly) {
            bw.close();
        }
    }

    public void initGet(String container) throws IOException {
        oraArray = new ReadPointerCollection(container, 1000);

    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            oraArray.close();
            File file = new File(container);
            // file.delete();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    @Override
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

}
