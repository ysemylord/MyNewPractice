package com.kotlin_coroutines.suspend_demo;


import com.kotlin_coroutines.A2SnippetKt;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class A2SnipetJava {
    public static void main(String[] args) {
        A2SnippetKt.suspendFunc02(new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println("result" + o);
            }
        });
    }
}
