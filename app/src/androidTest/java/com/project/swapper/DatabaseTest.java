package com.project.swapper;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.project.swapper.database.WAPDao;
import com.project.swapper.database.WAPDatabase;
import com.project.swapper.database.WAPItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private WAPDao dao;
    private WAPDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, WAPDatabase.class).build();
        dao = db.wapDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void testInsertion() {
        WAPItem item = new WAPItem("abc", "def");
        dao.insert(item);
        List<WAPItem> ret = dao.listAll();

        assertEquals(1, ret.size());
        assertEquals("abc", ret.get(0).getBssid());
        assertEquals("def", ret.get(0).getPassword());
    }

    @Test
    public void testUpdate() {
        WAPItem item = new WAPItem("abc", "def");
        dao.insert(item);

        dao.update("abc", "xyz");
        List<WAPItem> ret = dao.listAll();

        assertEquals(1, ret.size());
        assertEquals("abc", ret.get(0).getBssid());
        assertEquals("xyz", ret.get(0).getPassword());
    }

    @Test
    public void testContains() {
        WAPItem item = new WAPItem("1", "111");
        dao.insert(item);
        WAPItem item2 = new WAPItem("2131231", "23423253252");
        dao.insert(item2);
        WAPItem item3 = new WAPItem("2342523562", "fgdgdfsgdfg");
        dao.insert(item3);

        List<WAPItem> ret = dao.contains("0");
        assertEquals(0, ret.size());

        ret = dao.contains("1");
        assertEquals(1, ret.size());
        assertEquals("1", ret.get(0).getBssid());
        assertEquals("111", ret.get(0).getPassword());
    }
}
