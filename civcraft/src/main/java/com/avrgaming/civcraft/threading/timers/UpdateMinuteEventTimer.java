/*************************************************************************
 *
 * AVRGAMING LLC
 * __________________
 *
 *  [2013] AVRGAMING LLC
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of AVRGAMING LLC and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to AVRGAMING LLC
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from AVRGAMING LLC.
 */
package com.avrgaming.civcraft.threading.timers;

import cn.hutool.core.util.StrUtil;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.structure.Structure;
import com.avrgaming.civcraft.threading.CivAsyncTask;
import com.avrgaming.civcraft.threading.TaskMaster;
import com.avrgaming.civcraft.threading.tasks.FisheryAsyncTask;
import com.avrgaming.civcraft.threading.tasks.MobGrinderAsyncTask;
import com.avrgaming.civcraft.util.BlockCoord;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 20s执行
 * 有关钓鱼和怪物磨床
 */
public class UpdateMinuteEventTimer extends CivAsyncTask {

    public static ReentrantLock lock = new ReentrantLock();

    public UpdateMinuteEventTimer() {
    }

    @Override
    public void run() {
        if (!lock.tryLock()) {
            return;
        }
        try {
            // Loop through each structure, if it has an update function call it in another async process
            Iterator<Entry<BlockCoord, Structure>> iter = CivGlobal.getStructureIterator();

            while (iter.hasNext()) {
                Structure struct = iter.next().getValue();

                if (!struct.isActive())
                    continue;

                try {
                    if (StrUtil.isNotBlank(struct.getUpdateEvent())) {
                        if ("mobGrinder_process".equals(struct.getUpdateEvent())) {
                            if (!CivGlobal.mobGrinderEnabled) {
                                continue;
                            }
                            TaskMaster.asyncTask("mobGrinder-" + struct.getCorner().toString(), new MobGrinderAsyncTask(struct), 0);
                        } else if ("fish_hatchery_process".equals(struct.getUpdateEvent())) {
                            if (!CivGlobal.fisheryEnabled) {
                                continue;
                            }
                            Random rand = new Random();
                            if (rand.nextInt(5) <= 1) {
                                TaskMaster.asyncTask("fishHatchery-" + struct.getCorner().toString(), new FisheryAsyncTask(struct), 0);
                            }
                        }
                    }

//                    struct.onUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    //We need to catch any exception so that an error in one town/structure/good does not
                    //break things for everybody.
                    //TODO log exception into a file or something...
                    //				if (struct.getTown() == null) {
                    //					RJ.logException("TownUnknown struct:"+struct.config.displayName, e);
                    //				} else {
                    //					RJ.logException(struct.town.getName()+":"+struct.config.displayName, e);
                    //				}
                }
            }

//            for (Wonder wonder : CivGlobal.getWonders()) {
//                wonder.onUpdate();
//            }


//            for (Camp camp : CivGlobal.getCamps()) {
//                if (!camp.sifterLock.isLocked()) {
//                    TaskMaster.asyncTask(new CampUpdateTick(camp), 0);
//                }
//            }

        } finally {
            lock.unlock();
        }

    }

}
