package com.example.customview.MyLayoutInflaterFactory;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class MyLayoutInflaterFactory implements LayoutInflater.Factory2 {

    final Object[] mConstructorArgs = new Object[2];
    static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private LayoutInflater.Filter mFilter;
    private HashMap<String, Boolean> mFilterMap;
    Context mContext;

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app."
    };

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        mContext = context;
        Log.i("MyLayoutInflaterFactory", "onCreateView");
        View view = null;

        final Object lastContext = mConstructorArgs[0];
        mConstructorArgs[0] = context;
        try {
            if (-1 == name.indexOf('.')) {
                view = onCreateView(parent, name, attrs);
            } else {
                view = createView(name, null, attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mConstructorArgs[0] = lastContext;
        }


        return view;
    }

    protected View onCreateView(View parent, String name, AttributeSet attrs)
            throws ClassNotFoundException {
        return onCreateView(name, attrs);
    }

    protected View onCreateView(String name, AttributeSet attrs)
            throws ClassNotFoundException {

        for (String prefix : sClassPrefixList) {
            try {
                View view = createView(name, prefix, attrs);
                if (view != null) {
                    return view;
                }
            } catch (ClassNotFoundException e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }

        return createView(name, "android.view.", attrs);
    }

    public final View createView(String name, String prefix, AttributeSet attrs)
            throws ClassNotFoundException, InflateException {

        //
        if (name.contains("InflaterTagView")) {
            int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++) {
                Log.i(name + "attr name：", attrs.getAttributeName(i) + "|" + attrs.getAttributeValue(i) );
            }
        }


        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor != null && !verifyClassLoader(constructor)) {
            constructor = null;
            sConstructorMap.remove(name);
        }
        Class<? extends View> clazz = null;

        try {


            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                clazz = mContext.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                if (mFilter != null && clazz != null) {
                    boolean allowed = mFilter.onLoadClass(clazz);
                    if (!allowed) {
                        failNotAllowed(name, prefix, attrs);
                    }
                }
                constructor = clazz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            } else {
                // If we have a filter, apply it to cached constructor
                if (mFilter != null) {
                    // Have we seen this name before?
                    Boolean allowedState = mFilterMap.get(name);
                    if (allowedState == null) {
                        // New class -- remember whether it is allowed
                        clazz = mContext.getClassLoader().loadClass(
                                prefix != null ? (prefix + name) : name).asSubclass(View.class);

                        boolean allowed = clazz != null && mFilter.onLoadClass(clazz);
                        mFilterMap.put(name, allowed);
                        if (!allowed) {
                            failNotAllowed(name, prefix, attrs);
                        }
                    } else if (allowedState.equals(Boolean.FALSE)) {
                        failNotAllowed(name, prefix, attrs);
                    }
                }
            }

            Object lastContext = mConstructorArgs[0];
            if (mConstructorArgs[0] == null) {
                // Fill in the context if not already within inflation.
                mConstructorArgs[0] = mContext;
            }
            Object[] args = mConstructorArgs;
            args[1] = attrs;

            final View view = constructor.newInstance(args);


            mConstructorArgs[0] = lastContext;
            return view;

        } catch (NoSuchMethodException e) {
            final InflateException ie = new InflateException(attrs.getPositionDescription()
                    + ": Error inflating class " + (prefix != null ? (prefix + name) : name), e);

            throw ie;

        } catch (ClassCastException e) {
            // If loaded class is not a View subclass
            final InflateException ie = new InflateException(attrs.getPositionDescription()
                    + ": Class is not a View " + (prefix != null ? (prefix + name) : name), e);

            throw ie;
        } catch (ClassNotFoundException e) {
            // If loadClass fails, we should propagate the exception.
            throw e;
        } catch (Exception e) {
            final InflateException ie = new InflateException(
                    attrs.getPositionDescription() + ": Error inflating class "
                            + (clazz == null ? "<unknown>" : clazz.getName()), e);
            e.printStackTrace();
            throw ie;
        } finally {

        }
    }

    private static final ClassLoader BOOT_CLASS_LOADER = LayoutInflater.class.getClassLoader();

    private final boolean verifyClassLoader(Constructor<? extends View> constructor) {
        final ClassLoader constructorLoader = constructor.getDeclaringClass().getClassLoader();
        if (constructorLoader == BOOT_CLASS_LOADER) {
            // fast path for boot class loader (most common case?) - always ok
            return true;
        }
        // in all normal cases (no dynamic code loading), we will exit the following loop on the
        // first iteration (i.e. when the declaring classloader is the contexts class loader).
        ClassLoader cl = mContext.getClassLoader();
        do {
            if (constructorLoader == cl) {
                return true;
            }
            cl = cl.getParent();
        } while (cl != null);
        return false;
    }

    private void failNotAllowed(String name, String prefix, AttributeSet attrs) {
        throw new InflateException(attrs.getPositionDescription()
                + ": Class not allowed to be inflated " + (prefix != null ? (prefix + name) : name));
    }

    public void setFilter(LayoutInflater.Filter filter) {
        mFilter = filter;
        if (filter != null) {
            mFilterMap = new HashMap<String, Boolean>();
        }
    }
}
