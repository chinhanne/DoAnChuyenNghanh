import React, { Component } from 'react';

export default class Comments extends Component {
  constructor(props) {
    super(props);
    this.state = {
      comments: [
        { id: 1, text: 'Sản phẩm rất tuyệt vời!', rating: 5, replies: [] },
        { id: 2, text: 'Giá cả hợp lý.', rating: 4, replies: [] },
        { id: 3, text: 'Không hài lòng lắm.', rating: 2, replies: [] },
        { id: 4, text: 'Chất lượng sản phẩm đáng đồng tiền.', rating: 5, replies: [] },
        { id: 5, text: 'Giao hàng chậm.', rating: 3, replies: [] }
      ],
      newComment: '',
      newRating: 5,
      filterRating: 0,
      replyComment: '', // Bình luận trả lời
      replyToCommentId: null // ID bình luận để trả lời
    };
  }

  handleInputChange = (event) => {
    this.setState({ newComment: event.target.value });
  };

  handleRatingChange = (event) => {
    this.setState({ newRating: parseInt(event.target.value) });
  };

  handleFilterRatingChange = (event) => {
    this.setState({ filterRating: parseInt(event.target.value) });
  };

  handleSubmit = (event) => {
    event.preventDefault();
    const { newComment, newRating, comments } = this.state;
    if (newComment.trim()) {
      const newCommentObj = {
        id: comments.length + 1,
        text: newComment,
        rating: newRating,
        replies: [] // Khởi tạo mảng replies
      };
      this.setState({
        comments: [...comments, newCommentObj],
        newComment: '',
        newRating: 5
      });
    }
  };

  // Xử lý gửi phản hồi
  handleReplyInputChange = (event) => {
    this.setState({ replyComment: event.target.value });
  };

  handleReplySubmit = (event, commentId) => {
    event.preventDefault();
    const { replyComment, comments } = this.state;
    if (replyComment.trim()) {
      const updatedComments = comments.map((comment) => {
        if (comment.id === commentId) {
          return {
            ...comment,
            replies: [
              ...comment.replies,
              { id: comment.replies.length + 1, text: replyComment }
            ]
          };
        }
        return comment;
      });
      this.setState({
        comments: updatedComments,
        replyComment: '', // Reset bình luận trả lời
        replyToCommentId: null // Reset ID bình luận đang trả lời
      });
    }
  };

  render() {
    const { comments, newComment, newRating, filterRating, replyComment } = this.state;

    // Lọc bình luận theo rating
    const filteredComments = filterRating
      ? comments.filter((comment) => comment.rating === filterRating)
      : comments;

    return (
      <div className="container mt-4">
        <h4>Comments</h4>

        {/* Form để thêm bình luận mới */}
        <form onSubmit={this.handleSubmit} className="mb-3">
          <div className="form-group">
            <textarea
              className="form-control"
              placeholder="Viết bình luận của bạn..."
              value={newComment}
              onChange={this.handleInputChange}
              rows="3"
              required
            ></textarea>
          </div>
          <div className="form-group">
            <label>Đánh giá:</label>
            <select
              className="form-control"
              value={newRating}
              onChange={this.handleRatingChange}
              required
            >
              {[5, 4, 3, 2, 1].map((rating) => (
                <option key={rating} value={rating}>
                  {rating} Sao
                </option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-primary mt-2">
            Gửi bình luận
          </button>
        </form>

        {/* Lọc bình luận theo rating */}
        <div className="mb-3">
          <label>Lọc bình luận theo đánh giá:</label>
          <select
            className="form-control"
            value={filterRating}
            onChange={this.handleFilterRatingChange}
          >
            <option value={0}>Tất cả</option>
            {[5, 4, 3, 2, 1].map((rating) => (
              <option key={rating} value={rating}>
                {rating} Sao
              </option>
            ))}
          </select>
        </div>

        {/* Hiển thị danh sách bình luận */}
        <div className="comment-list">
          <h5 className="mt-4">Danh sách bình luận:</h5>
          {filteredComments.length > 0 ? (
            filteredComments.map((comment) => (
              <div key={comment.id} className="card mb-2">
                <div className="card-body">
                  <p>{comment.text}</p>
                  <p>
                    <span style={{ fontSize: '1.2em', color: '#ffc107' }}>
                      {'★'.repeat(comment.rating)}
                      {'☆'.repeat(5 - comment.rating)}
                    </span>
                    <span> ({comment.rating} sao)</span>
                  </p>
                  {/* Phần trả lời */}
                  <div>
                    <button
                      className="btn btn-link"
                      onClick={() => this.setState({ replyToCommentId: comment.id })}
                    >
                      Trả lời
                    </button>
                    {this.state.replyToCommentId === comment.id && (
                      <form onSubmit={(event) => this.handleReplySubmit(event, comment.id)} className="mt-2">
                        <textarea
                          className="form-control"
                          placeholder="Viết phản hồi của bạn..."
                          value={replyComment}
                          onChange={this.handleReplyInputChange}
                          rows="2"
                          required
                        ></textarea>
                        <button type="submit" className="btn btn-primary mt-2">
                          Gửi phản hồi
                        </button>
                      </form>
                    )}
                  </div>

                  {/* Hiển thị phản hồi */}
                  {comment.replies.length > 0 && (
                    <div className="mt-2">
                      <h6>Phản hồi:</h6>
                      {comment.replies.map((reply) => (
                        <div key={reply.id} className="border-left pl-3 mb-2">
                          <p>{reply.text}</p>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            ))
          ) : (
            <p>Không có bình luận nào với đánh giá này.</p>
          )}
        </div>
      </div>
    );
  }
}
