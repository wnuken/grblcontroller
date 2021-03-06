/*
 *  /**
 *  * Copyright (C) 2017  Grbl Controller Contributors
 *  *
 *  * This program is free software; you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation; either version 2 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License along
 *  * with this program; if not, write to the Free Software Foundation, Inc.,
 *  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *  * <http://www.gnu.org/licenses/>
 *
 */

package in.co.gorest.grblcontroller.listeners;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.ArrayAdapter;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import in.co.gorest.grblcontroller.BR;
import in.co.gorest.grblcontroller.model.Constants;

public class ConsoleLoggerListener extends BaseObservable{

    @Bindable
    private final CircularFifoQueue<String> loggedMessagesQueue;

    private static ConsoleLoggerListener consoleLoggerListener = null;
    public static ConsoleLoggerListener getInstance(){
        if(consoleLoggerListener == null) consoleLoggerListener = new ConsoleLoggerListener();
        return consoleLoggerListener;
    }

    public static void resetClass(){
        consoleLoggerListener = new ConsoleLoggerListener();
    }

    private ConsoleLoggerListener(){
        loggedMessagesQueue = new CircularFifoQueue<>(Constants.CONSOLE_LOGGER_MAX_SIZE);
    }

    @Bindable
    public String[] getLoggedMessagesQueue(){
        return loggedMessagesQueue.toArray(new String[loggedMessagesQueue.size()]);
    }

    @Bindable
    public synchronized String getMessages(){
        StringBuilder messages = new StringBuilder();
        for(String msg: loggedMessagesQueue) messages.append(msg);
        return messages.toString();
    }

    public synchronized void setMessages(String newMessage){
        this.loggedMessagesQueue.offer("\n" + newMessage);
        notifyPropertyChanged(BR.messages);
    }

    public void clearMessages(){
        this.loggedMessagesQueue.clear();
        notifyPropertyChanged(BR.messages);
    }




}