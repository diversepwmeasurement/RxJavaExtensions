/*
 * Copyright 2016-2019 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.rxjava3.debug;

import hu.akarnokd.rxjava3.debug.SingleOnAssembly.OnAssemblySingleObserver;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/**
 * Wraps a Publisher and inject the assembly info.
 *
 * @param <T> the value type
 */
final class SingleOnAssemblyScalarSupplier<T> extends Single<T> implements ScalarSupplier<T> {

    final SingleSource<T> source;

    final RxJavaAssemblyException assembled;

    SingleOnAssemblyScalarSupplier(SingleSource<T> source) {
        this.source = source;
        this.assembled = new RxJavaAssemblyException();
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> observer) {
        source.subscribe(new OnAssemblySingleObserver<T>(observer, assembled));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        return ((ScalarSupplier<T>)source).get();
    }
}
