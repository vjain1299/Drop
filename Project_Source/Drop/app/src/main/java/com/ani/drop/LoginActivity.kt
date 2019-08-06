package com.ani.drop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ani.drop.DataAccess.mFirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mFirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("52444824151-i337k9pn5fpm1q5upr7fgbghnh2v32mm.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInButton : SignInButton = findViewById(R.id.sign_in_google)
        signInButton.setOnClickListener {
                signIn()
        }
        val emailSignInButton : Button = findViewById(R.id.sign_in_email)
        emailSignInButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                mFirebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener()
                { task ->
                    if (task.isSuccessful) {
                        Log.w("Firebase User: ", "User " + mFirebaseAuth?.currentUser?.uid)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // mFirebaseAuth?.createUserWithEmailAndPassword(email, password)
                    }
                }
            }
        }

    }

    fun signIn() {
        var signInIntent : Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001 /*This is the RC_SIGN_IN value*/)
    }
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9001) {
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask : Task<GoogleSignInAccount>) {
        try {
            var account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)
            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }   catch (e : ApiException) {
                Log.w("SignInActivity", "signInResult:failed code=" + e.statusCode)
                Snackbar.make(sign_in_email.rootView, "Login Failed", Snackbar.LENGTH_LONG)
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("SignInActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }

}
