package org.sopt.santamanitto.view.dialog

import android.view.View
import androidx.annotation.ColorRes
import androidx.fragment.app.DialogFragment

/**
 * Builder for generate RoundDialog
 * @author SEONGGYU96
 * @since 2020/12/07
 */
class RoundDialogBuilder {

    private var textSize: Float? = null

    private var title: String? = null
    private var contentText: String? = null
    private var invitationCode: String? = null

    private var enableCancel: Boolean = true
    private var contentIsBold: Boolean = false
    private var enableDivider: Boolean = true

    @ColorRes private var pointColor: Int? = null
    private var contentView: View? = null
    @ColorRes private var divideColor: Int? = null

    private var verticalButtons: List<RoundDialogButton>? = null
    private var horizontalButtons: List<RoundDialogButton>? = null

    private var invitationCodeCallback: (() -> Unit)? = null

    /**
     * You can get RoundDialog instance of DialogFragment by calling this.
     * You Should have apply at least one attribute before calling this.
     */
    fun build(): DialogFragment {
        if (title == null && verticalButtons == null && horizontalButtons == null && contentView == null &&
                contentText == null) {
            throw IllegalAccessException("At least one attribute should be applied. (Title or Content or Buttons")
        }
        return RoundDialog(title,
                contentView,
                contentText,
                contentIsBold,
                invitationCode,
                invitationCodeCallback,
                horizontalButtons,
                verticalButtons,
                textSize,
                pointColor,
                divideColor,
                enableCancel,
                enableDivider)
    }

    /**
     * Set title of RoundDialog.
     * It will be placed to top of dialog as bold.
     * @param title Title of dialog you want
     */
    fun setTitle(title: String): RoundDialogBuilder {
        this.title = title
        return this
    }

    /**
     * Add a horizontal button.
     * It will be placed to bottom of dialog.
     * You can dismiss dialog by click this button as a default.
     * And No other actions.
     * @param text Text of button.
     */
    fun addHorizontalButton(text: String): RoundDialogBuilder {
        return addHorizontalButton(text, true)
    }

    /**
     * Add a horizontal button.
     * It will be placed to bottom of dialog.
     * You can dismiss dialog by click this button as a default.
     * @param text Text of button.
     * @param listener Lambda called when this button is clicked.
     *                 You can reference the content of dialog, If you'd put a content
     */
    fun addHorizontalButton(text: String, listener: ((content: View?) -> Unit)?): RoundDialogBuilder {
        return addHorizontalButton(text, true, listener)
    }

    /**
     * Add a horizontal button.
     * It will be placed to bottom of dialog.
     * @param text Text of button.
     * @param canDismiss If you set it false, You cannot dismiss dialog even you click this button.
     */
    fun addHorizontalButton(text: String, canDismiss: Boolean): RoundDialogBuilder {
        return addHorizontalButton(text, canDismiss, null)
    }

    /**
     * Add a horizontal button.
     * It will be placed to bottom of dialog.
     * @param text Text of button.
     * @param canDismiss If you set it false, You cannot dismiss dialog even you click this button.
     * @param listener Lambda called when this button is clicked.
     *                 You can reference the content of dialog, If you'd put a content
     */
    fun addHorizontalButton(text: String, canDismiss: Boolean, listener: ((content: View?) -> Unit)?)
            : RoundDialogBuilder {
        if (horizontalButtons == null) {
            horizontalButtons = mutableListOf()
        }
        (horizontalButtons as MutableList<RoundDialogButton>).add(RoundDialogButton(text, canDismiss, listener))
        return this
    }

    /**
     * Add a vertical button.
     * It will be placed to bottom of dialog.
     * But It's always gonna be above horizontal buttons.
     * You can dismiss dialog by click this button as a default.
     * @param text Text of button.
     */
    fun addVerticalButton(text: String): RoundDialogBuilder {
        return addVerticalButton(text, true)
    }

    /**
     * Add a vertical button.
     * It will be placed to bottom of dialog.
     * But It's always gonna be above horizontal buttons.
     * You can dismiss dialog by click this button as a default.
     * @param text Text of button.
     * @param listener Lambda called when this button is clicked.
     *                 You can reference the content of dialog, If you'd put a content
     */
    fun addVerticalButton(text: String, listener: ((content: View?) -> Unit)?): RoundDialogBuilder {
        return addVerticalButton(text, true, listener)
    }

    /**
     * Add a vertical button.
     * It will be placed to bottom of dialog.
     * But It's always gonna be above horizontal buttons.
     * @param text Text of button.
     * @param canDismiss If you set it false, You cannot dismiss dialog even you click this button.
     */
    fun addVerticalButton(text: String, canDismiss: Boolean): RoundDialogBuilder {
        return addVerticalButton(text, canDismiss, null)
    }

    /**
     * Add a vertical button.
     * It will be placed to bottom of dialog.
     * But It's always gonna be above horizontal buttons.
     * @param text Text of button.
     * @param canDismiss If you set it false, You cannot dismiss dialog even you click this button.
     * @param listener Lambda called when this button is clicked.
     *                 You can reference the content of dialog, If you'd put a content
     */
    fun addVerticalButton(text: String, canDismiss: Boolean, listener: ((content: View?) ->
    Unit)?):
            RoundDialogBuilder {
        if (verticalButtons == null) {
            verticalButtons = mutableListOf()
        }
        (verticalButtons as MutableList<RoundDialogButton>).add(RoundDialogButton(text, canDismiss, listener))
        return this
    }

    /**
     * Add a content using layout
     * It will be placed to between title and buttons.
     * The content layout should be pre-made in xml.
     * It is recommended that width is "match_parent" and height is "wrap_content".
     * @param view Id of content layout. ex) R.layout.....
     */
    fun setContentView(view: View): RoundDialogBuilder {
        this.contentView = view
        return this
    }

    /**
     * Add a content as a text.
     * It will be placed to between title and buttons
     * @param contentText Text to be content.
     */
    fun setContentText(contentText: String): RoundDialogBuilder {
        return setContentText(contentText, false)
    }

    /**
     * Add a content as a text.
     * It will be placed to between title and buttons
     * @param contentText Text to be content.
     * @param isBold If you set this true, Text will be bold.
     */
    fun setContentText(contentText: String, isBold: Boolean): RoundDialogBuilder {
        this.contentText = contentText
        this.contentIsBold = isBold
        return this
    }

    /**
     * You can change whole of text size of dialog.
     * @param textSize Text size as SP
     */
    fun setTextSize(textSize: Float): RoundDialogBuilder {
        this.textSize = textSize
        return this
    }

    /**
     * Apply point color to background of title and buttons.
     * If you use this attribute, It is recommended to change divider color to match with it.
     * @param colorRes Drawable id of color. ex) R.color....
     */
    fun setPointColor(@ColorRes colorRes: Int) : RoundDialogBuilder {
        this.pointColor = colorRes
        return this
    }

    /**
     * Change divider color.
     * Default color is #E2E2E2.
     * @param colorRes Drawable id of color. ex) R.color....
     */
    fun setDividerColor(@ColorRes colorRes: Int) : RoundDialogBuilder {
        this.divideColor = colorRes
        return this
    }

    /**
     * You can make divider disabled if you set it to false.
     * @param enableDivider true --> enable, false --> false
     */
    fun enableDivider(enableDivider: Boolean) : RoundDialogBuilder {
        this.enableDivider = enableDivider
        return this
    }

    /**
     * If you don't want to close dialog by touching the outside of dialog, set it to false.
     * Default is true
     * @param enableCancel true --> cancelable, false --> not cancelable
     */
    fun enableCancel(enableCancel: Boolean) : RoundDialogBuilder {
        this.enableCancel = enableCancel
        return this
    }

    fun setInvitationCode(invitationCode: String, listener: () -> Unit): RoundDialogBuilder {
        this.invitationCode = invitationCode
        this.invitationCodeCallback = listener
        return this
    }
}