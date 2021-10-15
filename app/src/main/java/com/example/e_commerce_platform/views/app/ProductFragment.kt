package com.example.e_commerce_platform.views.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.databinding.FragmentProductBinding
import com.example.e_commerce_platform.model.CategoryProducts
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.Util
import com.example.e_commerce_platform.viewModel.ProductViewModel
import java.util.*


class ProductFragment : Fragment() {
    private val args: ProductFragmentArgs by navArgs()
    private var _binding:FragmentProductBinding?=null
    private val binding get() = _binding!!
    private var currentProduct:Product?= null
    private lateinit var selected:String
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(ProductViewModel::class.java)
        val items = Util.getNames<CategoryProducts>()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list,items)
        binding.autoCompleteCategories.setAdapter(adapter)
        binding.autoCompleteCategories.setOnItemClickListener { adapterView, _, i, _ ->
            val item = adapterView.getItemAtPosition(i).toString()
            selected = item
        }
        args.let {
            currentProduct = it.product
            setValues(currentProduct)
            binding.autoCompleteCategories.setText(selected)
            binding.updateProductBtn.setOnClickListener {
                updateValues()
            }
        }

    }

    private fun updateValues() {
        val productName = binding.productName.text.toString()
        val productPrice = binding.productPriceEditText.text.toString()
        val productDesc = binding.productDescriptionEditText.text.toString()
        val updateDate = Calendar.getInstance().time.toString()

       currentProduct?.let {
           val currentProduct = Product(it.productId,productName,productPrice,it.image,selected,productDesc,updateDate)
            viewModel.setData(currentProduct)
           observe()


        }
    }

    private fun observe() {
        viewModel.error.observe(viewLifecycleOwner,{
            if(it){
                val controller = NavHostFragment.findNavController(this)
                controller.navigate(R.id.action_productFragment_to_productsFragment)
            }
            else{
                Util.toastMakerL(requireContext(),getString(R.string.wrong_message))
            }
        })

    }

    private fun setValues(product: Product?) {
        binding.product = product
        if (product != null) {
            selected = product.category.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}