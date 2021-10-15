package com.example.e_commerce_platform.views.app

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.adapter.ProductsAdapter
import com.example.e_commerce_platform.databinding.FragmentProductsBinding
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.RecyclerViewClickInterface
import com.example.e_commerce_platform.util.Util
import com.example.e_commerce_platform.viewModel.HomeViewModel

class ProductsFragment : Fragment(),RecyclerViewClickInterface {
    private var _binding :FragmentProductsBinding ?=null
    private val binding get() = _binding!!
    private var mainList = ArrayList<Product>()
    private lateinit var adapter: ProductsAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getData()
        observeData()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val product = mainList[viewHolder.adapterPosition]
                mainList.removeAt(viewHolder.adapterPosition)
                viewModel.deleteProduct(product)
                mainList.clear()
                viewModel.getData()
            }
        }).attachToRecyclerView(binding.allProductsList)
    }

    private fun observeData() {
        viewModel.productMLDModel.observe(viewLifecycleOwner, {
            it.let {
                mainList.clear()
                mainList.addAll(it)

                adapter = ProductsAdapter(mainList,this)
                binding.allProductsList.adapter = adapter

            }

        })
        viewModel.isSuccess.observe(viewLifecycleOwner,{
            if (!it){
                binding.idOnlineImage.visibility = View.VISIBLE
                binding.floatingActionAddProduct.visibility = View.GONE
                binding.searchView.visibility = View.GONE
                binding.filterProductsImage.visibility = View.GONE
                Util.toastMakerL(requireContext(),getString(R.string.internet_connection))
            }
            else{
                binding.idOnlineImage.visibility = View.GONE
                binding.floatingActionAddProduct.visibility = View.VISIBLE
                binding.searchView.visibility = View.VISIBLE
                binding.filterProductsImage.visibility = View.VISIBLE
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner,{
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
                if(mainList.size<1){
                    binding.addNewProductImage.visibility = View.VISIBLE
                }else{
                    binding.addNewProductImage.visibility = View.GONE
                }
            }

        })
    }

    private fun actions(){
        binding.floatingActionAddProduct.setOnClickListener {
            val action = ProductsFragmentDirections.actionProductsFragmentToNewProductFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!!.isNotEmpty()){
                    val searchList = ArrayList<Product>()
                    mainList.iterator().forEach { i ->
                        if(i.name ==p0 ){
                            searchList.add(i)
                        }
                        adapter = ProductsAdapter(searchList,this@ProductsFragment)
                        binding.allProductsList.adapter = adapter
                    }

                }else{

                    viewModel.getData()
                    observeData()
                }
                return true
            }

        })
        binding.filterProductsImage.setOnClickListener {
            val menu = PopupMenu(requireContext(),it)
            menu.inflate(R.menu.pop_up_options)
            menu.setOnMenuItemClickListener {item->
                if (item.itemId == R.id.sortByName) {

                }
                if (item.itemId == R.id.sort_by_categories) {

                }
                if(item.itemId == R.id.sortBySport){

                }
                return@setOnMenuItemClickListener false
            }
            menu.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onItemClick(product: Product) {
        val action = ProductsFragmentDirections.actionProductsFragmentToProductFragment(product)
        view?.let {
            Navigation.findNavController(it).navigate(action)
        }
    }


}