using System;

namespace StackOverflowDumpCodeBuilder
{
    public class Vote
    {
        public int Id { get; set; }
        public int PostId { get; set; }
        public int VoteTypeId { get; set; }
        public DateTime CreationDate { get; set; }
        public int UserId { get; set; }
    }
}