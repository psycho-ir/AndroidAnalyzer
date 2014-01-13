package com.sarabadani.android.analyzer.repository;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import com.sarabadani.android.analyzer.model.Contact;
import com.sarabadani.android.analyzer.model.ContactwithNoPhoto;

/**
 * Created by soroosh on 1/13/14.
 */
public class ContactRepository extends AbstractRepository {

    public ContactRepository(Context context) {
        super(context);
    }

    public Contact findContactWithPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));


        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int photoIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);
//        final int thumbnailIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            final String name = cursor.getString(nameIndex);
//            final Uri thumbnailUri = Uri.parse(cursor.getString(thumbnailIndex));
            Bitmap contactImage = queryContactImage(cursor.getInt(photoIndex));
            final Bitmap photo = contactImage;
            if (contactImage == null) {
                return new ContactwithNoPhoto(name);
            }
            return new Contact(name, Uri.parse(""), photo);
        }
        return Contact.NullContact;
    }

    private Bitmap queryContactImage(int imageDataRow) {
        Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{
                ContactsContract.CommonDataKinds.Photo.PHOTO
        }, ContactsContract.Data._ID + "=?", new String[]{
                Integer.toString(imageDataRow)
        }, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }
    }
}
