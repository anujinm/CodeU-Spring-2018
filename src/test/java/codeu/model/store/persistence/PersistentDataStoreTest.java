package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for PersistentDataStore. The PersistentDataStore class relies on DatastoreService,
 * which in turn relies on being deployed in an AppEngine context. Since this test doesn't run in
 * AppEngine, we use LocalServiceTestHelper to do all of the AppEngine setup so we can test. More
 * info: https://cloud.google.com/appengine/docs/standard/java/tools/localunittesting
 */
public class PersistentDataStoreTest {

  private PersistentDataStore persistentDataStore;
  private final LocalServiceTestHelper appEngineTestHelper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setup() {
    appEngineTestHelper.setUp();
    persistentDataStore = new PersistentDataStore();
  }

  @After
  public void tearDown() {
    appEngineTestHelper.tearDown();
  }

  @Test
  public void testSaveAndLoadUsers() throws PersistentDataStoreException {
    UUID idOne = UUID.randomUUID();
    String nameOne = "test_username_one";
    String passwordOne = "password_one";
    Instant creationOne = Instant.ofEpochMilli(1000);
    String photo_urlOne = "https://i1.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?resize=256%2C256&quality=100";
    String user_skillsOne = "skills_one";
	String aboutOne = "about_one";
    String emailOne = "one@gmail.com";
	User inputUserOne = new User(idOne, nameOne, passwordOne, creationOne, photo_urlOne, user_skillsOne, aboutOne, emailOne);

    UUID idTwo = UUID.randomUUID();
    String nameTwo = "test_username_two";
    String passwordTwo = "password_two";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    String photo_urlTwo = "https://i1.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?resize=256%2C256&quality=100";
    String user_skillsTwo = "skills_two";
	String aboutTwo = "about_two";
    String emailTwo = "one@gmail.com";
	User inputUserTwo = new User(idTwo, nameTwo, passwordTwo, creationTwo, photo_urlTwo, user_skillsTwo, aboutTwo, emailTwo);

    // save
    persistentDataStore.writeThrough(inputUserOne);
    persistentDataStore.writeThrough(inputUserTwo);

    // load
    List<User> resultUsers = persistentDataStore.loadUsers();

    // confirm that what we saved matches what we loaded
    User resultUserOne = resultUsers.get(0);
    Assert.assertEquals(idOne, resultUserOne.getId());
    Assert.assertEquals(nameOne, resultUserOne.getName());
    Assert.assertEquals(passwordOne, resultUserOne.getPassword());
    Assert.assertEquals(creationOne, resultUserOne.getCreationTime());
    Assert.assertEquals(photo_urlOne, resultUserOne.getPhotoURL());
    Assert.assertEquals(user_skillsOne, resultUserOne.getSkills());
    Assert.assertEquals(aboutOne, resultUserOne.getAbout());


    User resultUserTwo = resultUsers.get(1);
    Assert.assertEquals(idTwo, resultUserTwo.getId());
    Assert.assertEquals(nameTwo, resultUserTwo.getName());
    Assert.assertEquals(passwordTwo, resultUserTwo.getPassword());
    Assert.assertEquals(creationTwo, resultUserTwo.getCreationTime());
    Assert.assertEquals(photo_urlTwo, resultUserTwo.getPhotoURL());
    Assert.assertEquals(user_skillsTwo, resultUserTwo.getSkills());
    Assert.assertEquals(aboutTwo, resultUserTwo.getAbout());
  }

  @Test
  public void testSaveAndLoadConversations() throws PersistentDataStoreException {
    UUID idOne = UUID.randomUUID();
    UUID ownerOne = UUID.randomUUID();
    String titleOne = "Test_Title";
    Instant creationOne = Instant.ofEpochMilli(1000);
    Conversation inputConversationOne = new Conversation(idOne, ownerOne, titleOne, creationOne);

    UUID idTwo = UUID.randomUUID();
    UUID ownerTwo = UUID.randomUUID();
    String titleTwo = "Test_Title_Two";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    Conversation inputConversationTwo = new Conversation(idTwo, ownerTwo, titleTwo, creationTwo);

    // save
    persistentDataStore.writeThrough(inputConversationOne);
    persistentDataStore.writeThrough(inputConversationTwo);

    // load
    List<Conversation> resultConversations = persistentDataStore.loadConversations();

    // confirm that what we saved matches what we loaded
    Conversation resultConversationOne = resultConversations.get(0);
    Assert.assertEquals(idOne, resultConversationOne.getId());
    Assert.assertEquals(ownerOne, resultConversationOne.getOwnerId());
    Assert.assertEquals(titleOne, resultConversationOne.getTitle());
    Assert.assertEquals(creationOne, resultConversationOne.getCreationTime());

    Conversation resultConversationTwo = resultConversations.get(1);
    Assert.assertEquals(idTwo, resultConversationTwo.getId());
    Assert.assertEquals(ownerTwo, resultConversationTwo.getOwnerId());
    Assert.assertEquals(titleTwo, resultConversationTwo.getTitle());
    Assert.assertEquals(creationTwo, resultConversationTwo.getCreationTime());
  }

  @Test
  public void testSaveAndLoadMessages() throws PersistentDataStoreException {
    UUID idOne = UUID.randomUUID();
    UUID conversationOne = UUID.randomUUID();
    UUID authorOne = UUID.randomUUID();
    String contentOne = "test content one";
    Instant creationOne = Instant.ofEpochMilli(1000);
    Message inputMessageOne =
        new Message(idOne, conversationOne, authorOne, contentOne, creationOne);

    UUID idTwo = UUID.randomUUID();
    UUID conversationTwo = UUID.randomUUID();
    UUID authorTwo = UUID.randomUUID();
    String contentTwo = "test content one";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    Message inputMessageTwo =
        new Message(idTwo, conversationTwo, authorTwo, contentTwo, creationTwo);

    // save
    persistentDataStore.writeThrough(inputMessageOne);
    persistentDataStore.writeThrough(inputMessageTwo);

    // load
    List<Message> resultMessages = persistentDataStore.loadMessages();

    // confirm that what we saved matches what we loaded
    Message resultMessageOne = resultMessages.get(0);
    Assert.assertEquals(idOne, resultMessageOne.getId());
    Assert.assertEquals(conversationOne, resultMessageOne.getConversationId());
    Assert.assertEquals(authorOne, resultMessageOne.getAuthorId());
    Assert.assertEquals(contentOne, resultMessageOne.getContent());
    Assert.assertEquals(creationOne, resultMessageOne.getCreationTime());

    Message resultMessageTwo = resultMessages.get(1);
    Assert.assertEquals(idTwo, resultMessageTwo.getId());
    Assert.assertEquals(conversationTwo, resultMessageTwo.getConversationId());
    Assert.assertEquals(authorTwo, resultMessageTwo.getAuthorId());
    Assert.assertEquals(contentTwo, resultMessageTwo.getContent());
    Assert.assertEquals(creationTwo, resultMessageTwo.getCreationTime());
  }
}
