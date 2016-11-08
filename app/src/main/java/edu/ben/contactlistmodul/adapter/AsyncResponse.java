package edu.ben.contactlistmodul.adapter;

import edu.ben.contactlistmodul.contactAPI.objects.contacts.ContactList;

/**
 * 1. This Interface is allows us to pass back the searchContactsAdapter to the
*     Activity after we init the adapter in the AsyncTask services.
 *
 * 2. We declare the interface in the AsyncTask class as a field. and using
 *    the declare obj to call the processFinish method, and pass the init
 *    adapter in the onPostExecute method.
 *
 * 3. In the Activity. we Implementing the AsyncResponse Interface, by override
 *    the processFinish method and init the local activity adapter, from the result
 *    adapter we get from the processFinish interface method.
 *
 * after this we can use the filter method to filter the Contacts list in the Recycler view.
 */

public interface AsyncResponse {
    void processFinish(ContactList contacts);
    void processFinish(SearchContactsAdapter outputAdapter, ContactList contacts);
}
