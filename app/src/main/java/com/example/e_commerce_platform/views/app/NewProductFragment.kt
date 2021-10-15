package com.example.e_commerce_platform.views.app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.databinding.FragmentNewProductBinding
import com.example.e_commerce_platform.model.CategoryProducts
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.Util
import com.example.e_commerce_platform.viewModel.NewProductViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NewProductFragment : Fragment() {
    private var _binding: FragmentNewProductBinding?= null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var bitmap: Bitmap? = null
    private var uri: Uri?= null
    private lateinit var selectedCategory:String
    private lateinit var viewModel: NewProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentNewProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewProductViewModel::class.java)
        val items = Util.getNames<CategoryProducts>()
        val adapter = ArrayAdapter(requireContext(),R.layout.item_list,items)
        selectedCategory = CategoryProducts.BOOKS.name
        binding.autoCompleteCategories.setAdapter(adapter)
        registerLauncher()
        actions()


    }
    private fun actions(){
        binding.productImage.setOnClickListener {
            //selectProductImage(it)
        }
        Glide.with(requireContext()).load(Util.imgUrl).into(binding.productImage)
        binding.addNewProductBtn.setOnClickListener {
            saveNewProduct()
        }
        binding.autoCompleteCategories.setOnItemClickListener { adapterView, _, i, _ ->
            val item = adapterView.getItemAtPosition(i).toString()
            selectedCategory = item
        }
    }

    private fun saveNewProduct() {
        val productName = binding.productName.text.toString()
        val productPrice = binding.productPriceEditText.text.toString()
        val productDesc = binding.productDescriptionEditText.text.toString()
        val dateNow = Calendar.getInstance().time.toString()
        val uuid = UUID.randomUUID()
        val product = Product(uuid.toString(),productName,productPrice,Util.imgUrl,selectedCategory,productDesc,dateNow)
        viewModel.setData(product)
        observe()
    }

    private fun observe() {
        viewModel.error.observe(viewLifecycleOwner,{
            if(it){
                val controller = NavHostFragment.findNavController(this)
                controller.navigate(R.id.action_newProductFragment_to_productsFragment)
            }
            else{
                Util.toastMakerL(requireContext(),getString(R.string.wrong_message))
            }
        })
    }

    private fun selectProductImage(it: View) {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(it, getString(R.string.permission_for_gallery), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.give_permission)) {
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }
    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
                if (r.resultCode == Activity.RESULT_OK) {
                    val intentForResult = r.data
                    intentForResult?.let {
                         uri = it.data

                        uri?.let { img ->
                            try {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    val source = ImageDecoder.createSource(
                                        requireActivity().contentResolver,
                                        img
                                    )
                                    bitmap = ImageDecoder.decodeBitmap(source)
                                    binding.productImage.setImageBitmap(bitmap)
                                } else {
                                    bitmap = MediaStore.Images.Media.getBitmap(
                                        requireActivity().contentResolver,
                                        img
                                    )
                                    binding.productImage.setImageBitmap(bitmap)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                   Util.toastMakerL(requireContext(),getString(R.string.permission_needed))
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}