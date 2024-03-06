package id.rifqipadisiliwangi.tokopaerbe.presentation.feature

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.rifqipadisiliwangi.tokopaerbe.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    private val _email = "padi@mail.com"
    private val _password = "12345678"
    private val delayed = 2000L
    private val idlingResource = DelayIdlingResource(delayed)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(DelayIdlingResource(delayed))
    }

    @Test
    fun testLoginSuccess(){
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.tv_skip_onboarding)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
        onView(withId(R.id.tv_skip_onboarding)).perform(click())

        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.et_email_edit_login)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
        onView(withId(R.id.et_email_edit_login)).perform(typeText(_email))
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.et_email_edit_login)).check(matches(withText(_email)))
        IdlingRegistry.getInstance().unregister(idlingResource)

        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.et_password_edit_login)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
        onView(withId(R.id.et_password_edit_login)).perform(typeText(_password))
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.et_password_edit_login)).check(matches(withText(_password)))
        IdlingRegistry.getInstance().unregister(idlingResource)

        onView(withId(R.id.btn_login)).perform(click())
        IdlingRegistry.getInstance().unregister()
    }

}

class DelayIdlingResource(private val delayed : Long): IdlingResource{

    private var resourceCallback : IdlingResource.ResourceCallback? = null
    private val startTime = System.currentTimeMillis()
    override fun getName(): String {
        return DelayIdlingResource::class.java.simpleName
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    override fun isIdleNow(): Boolean {
        val elapsedTime = System.currentTimeMillis() - startTime
        val isIdle = elapsedTime >= delayed
        if (isIdle && resourceCallback != null){
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }
}