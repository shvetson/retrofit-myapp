package ru.shvets.myappretrofit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.shvets.myappretrofit.databinding.ActivityLoginBinding
import ru.shvets.myappretrofit.util.Extensions.toast
import ru.shvets.myappretrofit.util.FirebaseUtils.firebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var signInInputsArray: Array<EditText>
    private lateinit var login: EditText
    private lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login = binding.editTextLogin
        pass = binding.editTextPassword

        signInInputsArray = arrayOf(login, pass)

        binding.btnRegistration.setOnClickListener { onClickSignUp(it) }
        binding.btnLogin.setOnClickListener { onClickSignIn(it) }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("Welcome back!")
        }
    }

    private fun onClickSignUp(view: View) {
        if (notEmpty()) {

            firebaseAuth.createUserWithEmailAndPassword(login.text.toString(), pass.text.toString())
                .addOnCompleteListener { signUp ->

                    if (signUp.isSuccessful) {
                        toast("Signed up successfully")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else toast("Sign up failed")
                }
                .addOnFailureListener { exception -> exception.localizedMessage?.let { toast(it) } }
        }

    }

    private fun onClickSignIn(view: View) {
        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(login.text.toString(), pass.text.toString())
                .addOnCompleteListener { signIn ->

                    if (signIn.isSuccessful) {
                        toast("Signed in successfully")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        toast("Sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }

    private fun notEmpty(): Boolean = login.text.isNotEmpty()
            && pass.text.isNotEmpty()

    private fun sendEmailVerification() {
        firebaseAuth.currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to ${login.text}")
                }
            }
        }
    }
}