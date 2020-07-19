package com.unicorn.vehicle.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.di.Holder
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import florent37.github.com.rxlifecycle.RxLifecycle.disposeOnDestroy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

fun View.safeClicks(): Observable<Unit> = this.clicks()
    .throttleFirst(2000, TimeUnit.MILLISECONDS)

fun TextView.trimText() = this.text.toString().trim()

fun <T> Single<T>.observeOnMain(lifecycleOwner: LifecycleOwner): Single<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(disposeOnDestroy(lifecycleOwner))

fun <T> Observable<T>.observeOnMain(lifecycleOwner: LifecycleOwner): Observable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(disposeOnDestroy(lifecycleOwner))

fun RecyclerView.addDefaultItemDecoration(size: Int) {
    HorizontalDividerItemDecoration.Builder(context)
        .colorResId(R.color.md_grey_300)
        .size(size)
        .build().let { this.addItemDecoration(it) }
}

fun Context.startAct(actClass: Class<*>) {
    this.startActivity(Intent(this, actClass))
}

fun Context.toActAndFinish(actClass: Class<*>) {
    this.startActivity(Intent(this, actClass))
    this as Activity
    this.finish()
}

fun EditText.isEmpty(): Boolean {
    return this.trimText().isEmpty()
}

fun String.toast() {
    ToastUtils.showShort(this)
}

// https://www.jianshu.com/p/ea63991fbc05
inline fun <reified T> String.toBeanList(): List<T> = Holder.appComponent.gson().fromJson<List<T>>(this, ParameterizedTypeImpl(T::class.java))

class ParameterizedTypeImpl(val clz: Class<*>) : ParameterizedType {
    override fun getRawType(): Type = List::class.java

    override fun getOwnerType(): Type? = null

    override fun getActualTypeArguments(): Array<Type> = arrayOf(clz)
}

inline fun <reified T> String.toBean():T = Holder.appComponent.gson().fromJson(this,T::class.java)