package com.example.networkapplication.test_generic

import androidx.lifecycle.LiveData

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

/**
 * [LiveData] which publicly exposes [.setValue] and [.postValue] method.
 *
 * @param <T> The type of data hold by this instance
</T> */
open class MyMutableLiveData<T> : LiveData<T> {
    /**
     * Creates a MutableLiveData initialized with the given `value`.
     *
     * @param value initial value
     */
    constructor(value: T) : super(value) {}

    /**
     * Creates a MutableLiveData with no value assigned to it.
     */
    constructor() : super() {}

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }
}