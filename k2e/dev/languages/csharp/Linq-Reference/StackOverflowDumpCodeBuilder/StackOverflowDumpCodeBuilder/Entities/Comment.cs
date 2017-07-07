using System;

namespace StackOverflowDumpCodeBuilder
{
    public class Comment
    {
        public int Id { get; set; }
        public int PostId { get; set; }
        public string Text { get; set; }
        public DateTime CreationDate { get; set; }
        public int UserId { get; set; }
    }
}