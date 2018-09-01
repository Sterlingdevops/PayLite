package com.sterlingng.paylite.ui.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var mainActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun onMainActivityOpened() {
        onView(withId(R.id.log_in)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun onLoginActionTest() {
        onView(withId(R.id.log_in)).perform(click())

        onView(withId(R.id.username)).perform(typeText("rtukpe"))
        onView(withId(R.id.password)).perform(typeText("00000tukpe"), closeSoftKeyboard())
        onView(withId(R.id.sign_in)).perform(click())

        onView(withId(R.id.main_amount)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun onSignUpActionTest() {
        onView(withId(R.id.sign_in)).perform(click())

        onView(withId(R.id.first_name)).perform(typeText("Raymond"))
        onView(withId(R.id.last_name)).perform(typeText("Tukpe"), closeSoftKeyboard())
        onView(allOf(withId(R.id.next_name), isDisplayed())).perform(click())

        onView(allOf(withId(R.id.textView3), withText("PayliteContact details"), isDisplayed()))
        onView(allOf(
                childAtPosition(
                        childAtPosition(
                                withClassName(`is`("android.support.design.widget.TextInputLayout")),
                                0),
                        0),
                isDisplayed()))
                .perform(typeText("2348023221192"))
        onView(withId(R.id.email)).perform(typeText("rtukpe@gmail.com"))
        onView(withId(R.id.input_bvn)).perform(typeText("0000011111"), closeSoftKeyboard())
        onView(allOf(withId(R.id.next_email), isDisplayed())).perform(click())

        onView(allOf(withId(R.id.textView3), withText("Please enter the 5 digit code we just sent you by text."), isDisplayed()))
        for (i in 0..3) {
            onView(allOf(childAtPosition(
                    withId(R.id.pin_view), i),
                    isDisplayed())).perform(typeText(i.toString()), closeSoftKeyboard())
        }
        onView(allOf(withId(R.id.next_otp), isDisplayed())).perform(click())

        onView(allOf(withId(R.id.textView3), withText("Please enter the 5 digit code we just sent you by text."), isDisplayed()))
        onView(withId(R.id.input_password)).perform(typeText("taushsusjsnsh"))
        onView(withId(R.id.confirm_password)).perform(typeText("taushsusjsnsh"), closeSoftKeyboard())
        onView(allOf(withId(R.id.next_password), isDisplayed())).perform(click())

        onView(allOf(withId(R.id.textView3), withText("Create a transaction PIN"), isDisplayed()))
//        onView(withId(R.id.pin_view)).perform(typeText("0000"), closeSoftKeyboard())
        onView(allOf(withId(R.id.next_pin), isDisplayed())).perform(click())

        onView(allOf(withId(R.id.textView3), withText("Account Setup Completed!"), isDisplayed()))
        onView(allOf(withId(R.id.next_complete), isDisplayed())).perform(click())

        onView(withId(R.id.main_amount)).check(matches(isDisplayed()))
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}


















