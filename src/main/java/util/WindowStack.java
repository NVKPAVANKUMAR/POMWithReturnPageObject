package main.java.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

public final class WindowStack {
    public static final int WAIT_INTERVAL = 1;
    private volatile SetUniqueList handles = SetUniqueList.decorate(new LinkedList<String>());
    private Set<String> snapshot;
    private WebDriver driver;
    //private Thread thread;
    //private ReentrantLock lock;


    public WindowStack(WebDriver driver) {//, Thread thread) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.driver = driver;
        //this.lock = new ReentrantLock();
        //this.thread = thread;
    }
	/*
	protected ReentrantLock getLockObject() {
		return lock;
	}
	*/

    /**
     * Switches back to the previous window, without closing the current window.
     *
     * @return the window that was switched back to.
     */
    public String switchBack() {
        return switchBack(1, false);
    }

    /**
     * Switches back to a previously opened window, optionally closing all windows preceding it along the way.
     *
     * @param distance      number of windows to switch back to. must be between 1 and size-1, inclusive.
     * @param closePrevious if true, will close <code>distance</code> number of windows
     * @return the window handle that is now on the top of the stack, i.e., the window that was switched back to.
     */
    public String switchBack(int distance, boolean closePrevious) {
        //thread.interrupt();
        sleep();
        //lock();
        detectWindows();
        moveToTop(driver.getWindowHandle());

        //try {
        if (distance <= 0 || distance >= handles.size()) {
            throw new IllegalArgumentException("distance must be >= 1 or < WindowStack.size() when switching to a previous window.");
        }

        // distance is 1-based, handles is 0-based
        int index = distance;

        // this is the handle for the window we expect to land on
        // after this method completes.
        String targetHandle = (String) handles.get(index);

        if (closePrevious) {
            for (int i = 0; i < index; i++) {
                String curHandle = pop();
                forceClose(curHandle);
            }
        } else {
            // if closePrevious == true, then targetHandle will be located at the top of the stack anyway.
            // however, if closePrevious == false, then we need to move the targetHandle to the top of
            // the stack.
            handles.remove(index);
            push(targetHandle);
        }

        switchToTop();

        return targetHandle;
		/*} finally {
			//lock.unlock();
		}*/
    }

    /**
     * The size of the stack.
     *
     * @return the size of the stack.
     */
    public int size() {
        //thread.interrupt();
        //lock();
        detectWindows();
        //try {
        return handles.size();
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if empty, false otherwise.
     */
    private boolean isEmpty() {
        //thread.interrupt();
        //lock();
        detectWindows();
        //try {
        return size() == 0;
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }


    public boolean contains(String handle) {
        //thread.interrupt();
        //lock();
        //detectWindows();
        //try {
        return handles.contains(handle);
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Returns, but does not remove, the window handle on the top of the stack.
     *
     * @return window handle at the top of the stack, or null if the stack is empty.
     */
    private String peek() {
        //thread.interrupt();
        //lock();
        detectWindows();
        //try {
        String value = null;

        if (!isEmpty()) {
            value = (String) handles.get(0);
        }

        return value;
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Adds a window handle to the top of the stack.
     *
     * @param handle the window to move to the top of the stack. must not be null.
     */
    protected void push(String handle) {
        //thread.interrupt();
        //lock();
        //detectWindows();
        //try {
        if (handle == null) {
            throw new NullPointerException("WindowStack must contain only non-null elements");
        }

        handles.add(0, handle);
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Removes a window handle from the top of the stack, but does not close it.
     *
     * @return the window handle for the removed window.
     */
    private String pop() {
        //thread.interrupt();
        //lock();
        //detectWindows();
        //try {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an element from an empty WindowStack");
        }

        return (String) handles.remove(0);
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    protected void remove(String handle) {
        //thread.interrupt();
        //lock();
        //detectWindows();
        //try {
        handles.remove(handle);
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Move an existing window handle to the top of the stack if it is already present. Switch
     * the driver to that window.
     *
     * @param handle - the handle of the window to move/switch to
     */
    public WindowStack moveToTop(String handle) {
        //thread.interrupt();
        //sleep();
        //lock();
        detectWindows();
        //try {
        //if the handle is in the stack
        if (handles.contains(handle)) {
            //remove it, push it, then switch the driver to it
            handles.remove(handle);
            push(handle);
            driver.switchTo().window(handle);
        } else {
            //if the handle is not in the stack
            throw new NoSuchElementException("An attempt was made to move window with handle '" + handle + "' to the top of the stack but no such window exists. \r\nKnown handles: " + handles.toString());
        }
		/*} finally {
			lock.unlock();
		}*/

        return this;
    }

    public String switchToTop() {
        //thread.interrupt();
        sleep();
        //lock();
        detectWindows();
        //try {
        while (true) {
            try {
                driver.switchTo().window(this.peek());
                break;
            } catch (NoSuchWindowException e) {
                this.pop();
            }
        }
        return this.peek();
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/

    }

    /**
     * Closes the window that the driver is currently pointing to
     *
     * @return
     */
    public String closeWindow() {
        return switchBack(1, true);
    }

    /**
     * Converts the internal storage of window handles into a list of the same order and returns it.
     * Changes to the resulting list will NOT affect the original data structure.
     *
     * @return a shallow copy of the stack as a list in the same order
     */
    public List<String> toList() {
        //thread.interrupt();
        //lock();
        //detectWindows();
        //try {
        return Arrays.asList((String[]) handles.toArray(new String[0]));
		/*} finally {
			lock.unlock();
		}*/
    }

    /**
     * Returns a set representing the current known window handles. This set provides predictable
     * iteration order from the top of the stack down.
     *
     * @return The currently known window handles
     */
    public Set<String> getWindowHandles() {
        //thread.interrupt();
        //lock();
        detectWindows();
        //try {
        Set<String> val = new LinkedHashSet<>();

        for (Object handle : handles) {
            String h = (String) handle;
            val.add(h);
        }

        return val;
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Forcibly close a browser window.
     *
     * @param handle The window to close.
     */
    private void forceClose(String handle) {
        sleep();
        driver.switchTo().window(handle);

        String userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;", new Object[0]);

        //tc.log(LogLevel.debug, "Closing window: " + driver.getTitle() + " (" + handle + ") in browser " + userAgent + ".");

        // some magic to close ie 8 windows
        if (StringUtils.containsIgnoreCase(userAgent, "msie 8.0")) {
            ((JavascriptExecutor) driver).executeScript("window.open('', '_self').close();", new Object[0]);
        } else {
            driver.close();
        }
    }

    /**
     * Records a snapshot of the current window stack internally. This snapshot is referenced by
     * calls to waitForWindows() in order to detect when new windows have appeared.
     */
    public void takeSnapshot() {
        //lock();
        detectWindows();
        //try {
        snapshot = this.getWindowHandles();
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Fetches the size of the snapshot
     *
     * @return the size of the snapshot if it exists, 0 otherwise
     */
    public int getSnapshotSize() {
        //lock();
        //detectWindows();
        //try {
        return snapshot == null ? 0 : snapshot.size();
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    /**
     * Wait up to 1 second for the number of windows to differ from the snapshot
     *
     * @return the same WindowStack instance on which this was invoked, for method chaining
     */
    public WindowStack waitForWindows() {
        return waitForWindows(1);
    }

    /**
     * Wait up to timeout seconds for the number of windows to differ from the snapshot
     *
     * @param timeout The max wait time
     * @return the same WindowStack instance on which this was invoked, for method chaining
     */
    public WindowStack waitForWindows(int timeout) {
        sleep();
        //lock();
        detectWindows();
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            if (snapshot == null) {
                throw new IllegalStateException("WindowStack.snapshot() must always be invoked prior to any call to WindowStack.waitForWindows(...)");
            }

            EggTimer timer = new EggTimer(timeout, WindowStack.WAIT_INTERVAL * 2);
            timer.start();
            while (!timer.done()) {

                //addComment("\nWaiting For Windows:\nDriver:" + driver.getWindowHandles() + "\nStack:" + this.getWindowHandles() + "\nTimestamp:" + new Date());

                //find out how many windows are currently open
                int numCurrentHandles = this.getWindowHandles().size();

                //break when the number of windows differs from the snapshot
                // Here the second logic should be Provided a better logic to handle the confirmation windows)
                if (snapshot.size() != numCurrentHandles) {
                    timer.cancel();
                } else {
                    //unlock();
                    sleep();
                    //lock();
                    detectWindows();
                }
            }
        } finally {
            snapshot = null;
            // Set timeout back.
            driver.manage().timeouts().implicitlyWait(TestContext.getContext().getBrowserTimeout(), TimeUnit.SECONDS);
            //unlock();
        }
        return this;
    }
	
	/*private void unlock() {
		if(lock.isHeldByCurrentThread()) {
			lock.unlock();
		}
	}
	
	private void lock() {
		lock.lock();
		sleep();
		detectWindows();
	}*/

    /**
     * Wait up to timeout for the size of the stack to equal the specified size
     *
     * @param size    The expected number of windows to be open
     * @param timeout The max wait time
     * @return the same WindowStack instance on which this was invoked, for method chaining
     */
    public WindowStack waitForStackSize(int size, int timeout) {
        //lock();
        sleep();
        detectWindows();
        //try{
        EggTimer timer = new EggTimer(timeout, WindowStack.WAIT_INTERVAL * 2);
        timer.start();
        while (!timer.done()) {

            //find out how many windows are currently open
            int numCurrentHandles = this.getWindowHandles().size();

            //break when the number of windows equals the expected size
            if (numCurrentHandles == size) {
                timer.cancel();
            } else {
                sleep();
                detectWindows();
            }
        }
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
			
		}*/
        return this;
    }

    private void sleep() {
        sleep(WindowStack.WAIT_INTERVAL * 2000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // suppress
        }
    }

    private void sleep(int millis) {
        sleep((long) millis);
    }

    @Override
    public String toString() {
        //lock();
        //detectWindows();
        //try {
        return handles.toString();
		/*} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}*/
    }

    private void detectWindows() {
        // check for new handles
        Set<String> activeHandles = driver.getWindowHandles();
        for (String handle : activeHandles) {
            if (!this.contains(handle)) {
                this.push(handle);
            }
        }

        //removing old ones
        for (String handle : this.toList()) {
            if (!activeHandles.contains(handle)) {
                this.remove(handle);
            }
        }
		/*
		//checking for windows that no longer exist
		String handle = null;
		try {
			handle = driver.getWindowHandle();
		} catch (NoSuchWindowException e) {
			this.remove(handle);
		}*/
    }
}