package org.hibernate.presentation.query;

import jakarta.persistence.Tuple;
import org.hibernate.presentation.model.Forum;
import org.hibernate.presentation.model.Post;
import org.hibernate.query.SelectionQuery;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrate from clause subquery and lateral joins
 */
@DomainModel( annotatedClasses = { Forum.class, Post.class } )
@SessionFactory
public class FromClauseSubqueryExampleTest {
	@Test
	public void showFirstPostPerForum(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final SelectionQuery<Tuple> query = session.createSelectionQuery(
				"select f.name, firstPost.postTitle " +
					"from Forum f " +
					"left join lateral (" +
						"select post.title as postTitle " +
						"from f.posts post " +
						"order by post.id " +
						"limit 1" +
					") firstPost",
				Tuple.class
			);
			final List<Tuple> results = query.list();
			assertThat( results ).hasSize( 1 );
			assertThat( results.get( 0 ).get( 0 ) ).isEqualTo( "first forum" );
			assertThat( results.get( 0 ).get( 1 ) ).isEqualTo( "Welcome!" );
		} );
	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final Forum forum = new Forum( 1, "first forum" );
			session.persist( forum );

			session.persist( new Post( 1, "Welcome!", forum ) );
			session.persist( new Post( 2, "Hi!", forum ) );
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete Post" ).executeUpdate();
			session.createMutationQuery( "delete Forum" ).executeUpdate();
		} );
	}
}
