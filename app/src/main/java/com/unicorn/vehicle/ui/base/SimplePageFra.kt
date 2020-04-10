package com.unicorn.vehicle.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.defaultPageSize
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.base.PageResponse
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.ui_swipe_recycler.*

abstract class SimplePageFra<Model, K : BaseViewHolder> : BaseFra() {

    abstract val simpleAdapter: BaseQuickAdapter<Model, K>

    abstract fun loadPage(pageNo: Int): Single<PageResponse<Model>>

    protected open fun afterLoadFirstPage(total: Int) {

    }

    private val total
        get() = simpleAdapter.data.size

    private val pageNo
        get() = total / defaultPageSize + 1

    protected open val mRecyclerView: RecyclerView get() = recyclerView

    protected open val mSwipeRefreshLayout: SwipeRefreshLayout get() = swipeRefreshLayout

    override fun initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            simpleAdapter.bindToRecyclerView(this)
            simpleAdapter.setEnableLoadMore(true)
        }
    }

    override fun bindIntent() {
        mSwipeRefreshLayout.setOnRefreshListener { loadFirstPage() }
        simpleAdapter.setOnLoadMoreListener({ loadNextPage() }, mRecyclerView)
        loadFirstPage()
    }

    protected fun loadFirstPage() {
        mSwipeRefreshLayout.isRefreshing = true
        loadPage(1)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mSwipeRefreshLayout.isRefreshing = false
                    simpleAdapter.setNewData(it.items)
                    checkIsLoadAll(it)
                    afterLoadFirstPage(it.total)
                    // 在这里设置 empty view
//                    simpleAdapter.setEmptyView(R.layout.ui_no_order, mRecyclerView)
                },
                onError = {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            )
    }

    private fun loadNextPage() {
        loadPage(pageNo)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    simpleAdapter.loadMoreComplete()
                    simpleAdapter.addData(it.items)
                    checkIsLoadAll(it)
                },
                onError = {
                    simpleAdapter.loadMoreComplete()
                }
            )
    }

    private fun checkIsLoadAll(pageResponse: PageResponse<Model>) {
        val isLoadAll = total >= pageResponse.total // more safe but not exact
        if (isLoadAll) simpleAdapter.loadMoreEnd(true)
    }

    override val layoutId = R.layout.ui_swipe_recycler

}