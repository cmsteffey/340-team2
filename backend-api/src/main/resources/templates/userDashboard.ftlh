<#import "macros.ftlh" as macros>
<!DOCTYPE HTML>
<html lang="en">

<head>
    <link rel="stylesheet" href="/static/navbar.css">
    <link rel="stylesheet" href="/static/userDashboard.css">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
          rel="stylesheet">
</head>

<body>
<@macros.header/>

<main>
    <div class="container">
        <div class="featuredBox">
            <#assign coachIndex = 0>
            <#list accounts as account>
                <#if account.role?string == "COACH">
                    <div class="featuredCoachBox" onclick="openCoachModal(${coachIndex})">
                        <#assign coachIndex = coachIndex + 1>
                        <div class="featuredCoachContentBox">
                            <img id="coach1"
                                 src="<#if account.coachData?? && account.coachData.imageName??>/profile_picture/${account.coachData.imageName}<#else>/static/Guy.png</#if>"
                                 height="160px"/>
                        </div>
                        <div class="featuredCoachContentBox">
                            <h3>${account.username}</h3>
                            <hr class="divider">
                            <h4>
                                <#if account.coachData??>
                                    ${account.coachData.description!""}
                                </#if>
                            </h4>
                        </div>

                        <#if subscriptions[account.id?string]!false>
                            <form method="post" action="/unsubscribe" onclick="event.stopPropagation()">
                                <input type="hidden" name="coachId" value="${account.id}">
                                <button type="submit" class="subscribeBtn">Unsubscribe</button>
                            </form>
                        <#else>
                            <form method="post" action="/subscribe" onclick="event.stopPropagation()">
                                <input type="hidden" name="coachId" value="${account.id}">
                                <button type="submit" class="subscribeBtn">Subscribe</button>
                            </form>
                        </#if>
                    </div>
                </#if>
            </#list>
        </div>

        <div class="postBox">
            <div class="postBoxHeader">
                <div class="postHeaderBar">
                    <div class="searchCategories">
                        <span>Name</span>
                        <span>Post Time</span>
                        <span>Title</span>
                        <span>Content</span>
                    </div>
                </div>
            </div>

            <div class="searchResultsBox">
                <#list coachPosts as post>
                    <div class="searchResult" onclick="openPostModal(${post?index})" style="cursor: pointer;">
                        <span>${post.author.username}</span>
                        <span>${post.formattedDate}</span>
                        <span>${post.title}</span>
                        <span>${post.content}</span>
                    </div>
                </#list>
            </div>
        </div>

        <div class="eventBox">
            <#if upcomingEvents?? && upcomingEvents?has_content>
                <#list upcomingEvents as event>
                    <div class="event" onclick="openEventModal(${event?index})">
                        <div class="dateBox">
                            ${event.startTime?datetime("iso")?string("dd")}
                        </div>
                        <div class="event-info">
                            <span class="event-title">${event.title}</span>
                            <span class="event-time">${event.startTime?datetime("iso")?string("MMM dd, yyyy")}</span>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="event">
                    <p>No upcoming events at this time.</p>
                </div>
            </#if>
        </div>
    </div>

    <!-- Event Modal -->
    <div id="eventModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeEventModal()">&times;</span>
            <h2 id="modalTitle"></h2>
            <p id="modalDescription"></p>
            <p><strong>Host:</strong> <span id="modalHost"></span></p>
            <p><strong>Location:</strong> <span id="modalLocation"></span></p>
            <p><strong>Date & Time:</strong> <span id="modalDateTime"></span></p>
            <p><strong>Duration:</strong> <span id="modalDuration"></span></p>
            <p><strong>Cost:</strong> <span id="modalCost"></span></p>

            <button id="eventActionBtn" class="event-action-btn" onclick="handleEventAction()"></button>
        </div>
    </div>

    <!-- Coach Modal -->
    <div id="coachModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeCoachModal()">&times;</span>
            <h2 id="coachModalName"></h2>
            <p id="coachModalDescription"></p>

            <div class="rating-section">
                <h3>Rate this Coach</h3>
                <div class="star-rating" id="starRating">
                    <span class="star" data-rating="1">★</span>
                    <span class="star" data-rating="2">★</span>
                    <span class="star" data-rating="3">★</span>
                    <span class="star" data-rating="4">★</span>
                    <span class="star" data-rating="5">★</span>
                </div>
                <p id="selectedRatingText">Select a rating</p>
                <textarea id="reviewContent" placeholder="Write your review here..." rows="4"></textarea>
                <button class="event-action-btn join-btn" onclick="submitReview()">Submit Review</button>
            </div>

            <div class="appointment-section">
                <h3>Schedule an Appointment</h3>
                <div class="appointment-form">
                    <label for="appointmentDateTime">Select Date & Time:</label>
                    <input type="datetime-local" id="appointmentDateTime">

                    <label for="appointmentLength">Duration (minutes):</label>
                    <input type="number" id="appointmentLength" min="15" step="15" value="60" placeholder="60">

                    <button class="event-action-btn join-btn" onclick="bookAppointment()">Book Appointment</button>
                </div>
            </div>

            <div id="existingReviews">
                <h4>Reviews</h4>
                <div id="reviewsList"></div>
            </div>
        </div>
    </div>

    <div id="postModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closePostModal()">&times;</span>
            <h2 id="postModalTitle"></h2>
            <p id="postModalAuthor"></p>
            <p id="postModalDate"></p>
            <p id="postModalContent"></p>

            <div class="rating-section">
                <h3>Comments</h3>
                <textarea id="commentContent" placeholder="Write a comment..." rows="3"></textarea>
                <button class="event-action-btn join-btn" onclick="submitComment()">Post Comment</button>
            </div>

            <div id="existingComments">
                <h4>All Comments</h4>
                <div id="commentsList"></div>
            </div>
        </div>
    </div>

    <script>
        // Get current account ID from session
        const currentAccountId = ${currentAccount.id};

        // Store event data for modal
        const events = [
            <#if upcomingEvents?? && upcomingEvents?has_content>
            <#list upcomingEvents as event>
            {
                id: ${event.id},
                title: "${event.title?js_string}",
                description: "${(event.description!'')?js_string}",
                host: "${event.eventHost.username?js_string}",
                location: "${(event.eventAddress!'')?js_string}",
                dateTime: "${event.startTime?datetime("iso")?string("MMM dd, yyyy 'at' h:mm a")?js_string}",
                duration: "${(event.eventDuration / 60)?round} minutes",
                cost: "<#if event.eventCost gt 0>$${(event.eventCost / 100)?string("0.00")}<#else>Free</#if>",
                isJoined: ${(eventSubscriptions[event.id?string]!false)?c}
            }<#if event?has_next>,</#if>
            </#list>
            </#if>
        ];

        let currentEventIndex = null;

        const coaches = [
            <#list accounts as account>
            <#if account.role?string == "COACH">
            {
                id: ${account.id},
                username: "${account.username?js_string}",
                firstname: "${account.firstName?js_string}",
                lastname: "${account.lastName?js_string}",
                description: "<#if account.coachData??>${(account.coachData.description!'')?js_string}</#if>"
            }<#if account?has_next>,</#if>
            </#if>
            </#list>
        ];

        let currentCoachIndex = null;
        let selectedRating = 0;

        const posts = [
            <#list coachPosts as post>
            {
                id: ${post.id},
                title: "${post.title?js_string}",
                content: "${post.content?js_string}",
                author: "${post.author.username?js_string}",
                date: "${post.formattedDate?js_string}"
            }<#if post?has_next>,</#if>
            </#list>
        ];

        let currentPostIndex = null;

        function bookAppointment() {
            if (currentCoachIndex === null) {
                alert('Please select a coach');
                return;
            }

            const coach = coaches[currentCoachIndex];
            const dateTimeInput = document.getElementById('appointmentDateTime');
            const lengthInput = document.getElementById('appointmentLength');

            if (!dateTimeInput.value) {
                alert('Please select a date and time');
                return;
            }

            if (!lengthInput.value || lengthInput.value < 15) {
                alert('Please enter a valid duration (at least 15 minutes)');
                return;
            }

            const selectedDateTime = new Date(dateTimeInput.value);
            const now = new Date();

            if (selectedDateTime <= now) {
                alert('Please select a future date and time');
                return;
            }

            const timeInMillis = selectedDateTime.getTime();
            const lengthInSeconds = parseInt(lengthInput.value) * 60;

            // Create a form and submit it using POST with x-www-form-urlencoded
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/appointments';

            const timeInput = document.createElement('input');
            timeInput.type = 'hidden';
            timeInput.name = 'time';
            timeInput.value = timeInMillis.toString();
            form.appendChild(timeInput);

            const lengthHiddenInput = document.createElement('input');
            lengthHiddenInput.type = 'hidden';
            lengthHiddenInput.name = 'length';
            lengthHiddenInput.value = lengthInSeconds.toString();
            form.appendChild(lengthHiddenInput);

            const withInput = document.createElement('input');
            withInput.type = 'hidden';
            withInput.name = 'with';
            withInput.value = coach.id.toString();
            form.appendChild(withInput);

            document.body.appendChild(form);
            form.submit();
        }

        function initStarRating() {
            const stars = document.querySelectorAll('.star');
            const ratingText = document.getElementById('selectedRatingText');

            stars.forEach(star => {
                star.addEventListener('click', function() {
                    selectedRating = parseInt(this.getAttribute('data-rating'));
                    updateStarDisplay();
                    ratingText.textContent = 'Rating: ' + selectedRating + ' star' + (selectedRating !== 1 ? 's' : '');
                });

                star.addEventListener('mouseenter', function() {
                    const hoverRating = parseInt(this.getAttribute('data-rating'));
                    stars.forEach(s => {
                        const starRating = parseInt(s.getAttribute('data-rating'));
                        s.classList.toggle('selected', starRating <= hoverRating);
                    });
                });
            });

            document.getElementById('starRating').addEventListener('mouseleave', updateStarDisplay);
        }

        function updateStarDisplay() {
            const stars = document.querySelectorAll('.star');
            stars.forEach(star => {
                const starRating = parseInt(star.getAttribute('data-rating'));
                star.classList.toggle('selected', starRating <= selectedRating);
            });
        }

        async function submitReview() {
            if (currentCoachIndex === null) return;

            const coach = coaches[currentCoachIndex];
            const reviewContent = document.getElementById('reviewContent').value.trim();

            if (selectedRating === 0) {
                alert('Please select a rating');
                return;
            }

            if (!reviewContent) {
                alert('Please write a review');
                return;
            }

            const reviewData = {
                coach: { id: coach.id },
                rating: selectedRating,
                content: reviewContent
            };

            console.log('Submitting review:', reviewData);

            try {
                const response = await fetch('/reviews', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(reviewData)
                });

                console.log('Response status:', response.status);

                if (response.ok) {
                    const result = await response.json();
                    console.log('Review created:', result);
                    showNotification('Review submitted successfully!');
                    selectedRating = 0;
                    document.getElementById('reviewContent').value = '';
                    document.getElementById('selectedRatingText').textContent = 'Select a rating';
                    updateStarDisplay();
                    await loadReviews(coach.id);
                } else {
                    const errorText = await response.text();
                    console.error('Error response:', errorText);
                    alert('Failed to submit review. Status: ' + response.status + '\nError: ' + errorText);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred while submitting the review: ' + error.message);
            }
        }

        async function loadReviews(coachId) {
            try {
                const response = await fetch('/reviews/on/' + coachId);

                if (response.ok) {
                    const reviews = await response.json();
                    displayReviews(reviews);
                } else {
                    document.getElementById('reviewsList').innerHTML = '<p class="no-availability">Unable to load reviews</p>';
                }
            } catch (error) {
                console.error('Error loading reviews:', error);
                document.getElementById('reviewsList').innerHTML = '<p class="no-availability">Unable to load reviews</p>';
            }
        }

        function displayReviews(reviews) {
            const reviewsList = document.getElementById('reviewsList');

            if (!reviews || reviews.length === 0) {
                reviewsList.innerHTML = '<p class="no-availability">No reviews yet. Be the first to review!</p>';
                return;
            }

            reviewsList.innerHTML = reviews.map(review => {
                const date = new Date(review.postTime);
                const formattedDate = date.toLocaleDateString('en-US', {
                    year: 'numeric',
                    month: 'short',
                    day: 'numeric'
                });

                const stars = '★'.repeat(review.rating) + '☆'.repeat(5 - review.rating);
                const username = review.customer.username ? review.customer.username : 'Anonymous';

                return `
                    <div class="review-item">
                        <div class="review-header">
                            <span class="review-stars">` + stars + `</span>
                            <span class="review-date">` + formattedDate + `</span>
                        </div>
                        <div class="review-author">By: ` + username + `</div>
                        <div class="review-content">` + review.content + `</div>
                    </div>
                `;
            }).join('');
        }

        function openEventModal(index) {
            currentEventIndex = index;
            const event = events[index];
            const modal = document.getElementById('eventModal');

            document.getElementById('modalTitle').textContent = event.title;
            document.getElementById('modalDescription').textContent = event.description || 'No description available';
            document.getElementById('modalHost').textContent = event.host;
            document.getElementById('modalLocation').textContent = event.location || 'Location TBA';
            document.getElementById('modalDateTime').textContent = event.dateTime;
            document.getElementById('modalDuration').textContent = event.duration;
            document.getElementById('modalCost').textContent = event.cost;

            const btn = document.getElementById('eventActionBtn');

            if (event.isJoined) {
                btn.textContent = 'Leave Event';
                btn.className = 'event-action-btn unjoin-btn';
            } else {
                btn.textContent = 'Join Event';
                btn.className = 'event-action-btn join-btn';
            }

            modal.style.display = 'block';
        }

        function closeEventModal() {
            document.getElementById('eventModal').style.display = 'none';
            currentEventIndex = null;
        }

        async function handleEventAction() {
            if (currentEventIndex === null) return;

            const event = events[currentEventIndex];
            const btn = document.getElementById('eventActionBtn');

            // Disable button during request
            btn.disabled = true;
            const originalText = btn.textContent;
            btn.textContent = 'Loading...';

            try {
                if (event.isJoined) {
                    // Unjoin event using DELETE endpoint
                    const response = await fetch('/eventSubscription', {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            accountId: currentAccountId,
                            eventId: event.id
                        })
                    });

                    if (response.ok) {
                        // Update local state
                        event.isJoined = false;
                        btn.textContent = 'Join Event';
                        btn.className = 'event-action-btn join-btn';

                        // Show success message
                        showNotification('Successfully left the event');
                    } else {
                        const errorText = await response.text();
                        alert('Failed to leave event: ' + errorText);
                        btn.textContent = originalText;
                    }
                } else {
                    // Join event using POST endpoint
                    const response = await fetch('/eventSubscription', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            accountId: currentAccountId,
                            eventId: event.id
                        })
                    });

                    if (response.ok) {
                        // Update local state
                        event.isJoined = true;
                        btn.textContent = 'Leave Event';
                        btn.className = 'event-action-btn unjoin-btn';

                        // Show success message
                        showNotification('Successfully joined the event!');
                    } else {
                        const errorText = await response.text();
                        alert('Failed to join event: ' + errorText);
                        btn.textContent = originalText;
                    }
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred: ' + error.message);
                btn.textContent = originalText;
            } finally {
                btn.disabled = false;
            }
        }

        // Simple notification function
        function showNotification(message) {
            const notification = document.createElement('div');
            notification.className = 'notification';
            notification.textContent = message;
            notification.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                background: #4CAF50;
                color: white;
                padding: 16px 24px;
                border-radius: 4px;
                z-index: 10000;
                box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            `;
            document.body.appendChild(notification);

            setTimeout(() => {
                notification.style.opacity = '0';
                notification.style.transition = 'opacity 0.3s';
                setTimeout(() => notification.remove(), 300);
            }, 3000);
        }

        function openCoachModal(index) {
            currentCoachIndex = index;
            const coach = coaches[index];
            const modal = document.getElementById('coachModal');

            document.getElementById('coachModalName').textContent = coach.username + " - " + coach.firstname + " " + coach.lastname;
            document.getElementById('coachModalDescription').textContent = coach.description || 'No description available';

            selectedRating = 0;
            document.getElementById('reviewContent').value = '';
            document.getElementById('selectedRatingText').textContent = 'Select a rating';
            updateStarDisplay();

            const dateTimeInput = document.getElementById('appointmentDateTime');
            dateTimeInput.value = '';
            const now = new Date();
            now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
            dateTimeInput.min = now.toISOString().slice(0, 16);
            document.getElementById('appointmentLength').value = '60';

            loadReviews(coach.id);

            modal.style.display = 'block';
        }

        function closeCoachModal() {
            document.getElementById('coachModal').style.display = 'none';
            currentCoachIndex = null;
        }

        function openPostModal(index) {
            currentPostIndex = index;
            const post = posts[index];
            const modal = document.getElementById('postModal');

            document.getElementById('postModalTitle').textContent = post.title;
            document.getElementById('postModalAuthor').innerHTML = '<strong>Author:</strong> ' + post.author;
            document.getElementById('postModalDate').innerHTML = '<strong>Posted:</strong> ' + post.date;
            document.getElementById('postModalContent').textContent = post.content;

            document.getElementById('commentContent').value = '';

            loadComments(post.id);

            modal.style.display = 'block';
        }

        function closePostModal() {
            document.getElementById('postModal').style.display = 'none';
            currentPostIndex = null;
        }

        async function submitComment() {
            if (currentPostIndex === null) return;

            const post = posts[currentPostIndex];
            const commentContent = document.getElementById('commentContent').value.trim();

            if (!commentContent) {
                alert('Please write a comment');
                return;
            }

            const commentData = {
                post: { id: post.id },
                content: commentContent
            };

            console.log('Submitting comment:', commentData);

            try {
                const response = await fetch('/comment', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(commentData)
                });

                console.log('Response status:', response.status);

                if (response.ok) {
                    const result = await response.json();
                    console.log('Comment created:', result);
                    showNotification('Comment posted successfully!');
                    document.getElementById('commentContent').value = '';
                    await loadComments(post.id);
                } else {
                    const errorText = await response.text();
                    console.error('Error response:', errorText);
                    alert('Failed to post comment. Status: ' + response.status + '\nError: ' + errorText);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred while posting the comment: ' + error.message);
            }
        }

        async function loadComments(postId) {
            try {
                const response = await fetch('/comments/on/' + postId);

                if (response.ok) {
                    const comments = await response.json();
                    displayComments(comments);
                } else {
                    document.getElementById('commentsList').innerHTML = '<p class="no-availability">Unable to load comments</p>';
                }
            } catch (error) {
                console.error('Error loading comments:', error);
                document.getElementById('commentsList').innerHTML = '<p class="no-availability">Unable to load comments</p>';
            }
        }

        function displayComments(comments) {
            const commentsList = document.getElementById('commentsList');

            if (!comments || comments.length === 0) {
                commentsList.innerHTML = '<p class="no-availability">No comments yet. Be the first to comment!</p>';
                return;
            }

            commentsList.innerHTML = comments.map(comment => {
                const date = new Date(comment.postTime);
                const formattedDate = date.toLocaleDateString('en-US', {
                    year: 'numeric',
                    month: 'short',
                    day: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit'
                });

                const username = comment.customer.username ? comment.customer.username : 'Anonymous';

                return `
                    <div class="review-item">
                        <div class="review-header">
                            <span class="review-author">` + username + `</span>
                            <span class="review-date">` + formattedDate + `</span>
                        </div>
                        <div class="review-content">` + comment.content + `</div>
                    </div>
                `;
            }).join('');
        }

        window.onclick = function(event) {
            const eventModal = document.getElementById('eventModal');
            const coachModal = document.getElementById('coachModal');
            const postModal = document.getElementById('postModal');
            if (event.target == eventModal) {
                closeEventModal();
            } else if (event.target == coachModal) {
                closeCoachModal();
            } else if (event.target == postModal) {
                closePostModal();
            }
        }

        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeEventModal();
                closeCoachModal();
                closePostModal();
            }
        });

        document.addEventListener('DOMContentLoaded', initStarRating);
    </script>
</main>
</body>
</html>
