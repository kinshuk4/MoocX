using System;
using System.Collections.Generic;
using System.Xml.Linq;

namespace StackOverflowDumpCodeBuilder
{
    public class CommentMapper: BaseMapper
    {
        public IEnumerable<Comment> Map(IEnumerable<XElement> elements)
        {
            var result = new List<Comment>();
            foreach (XElement element in elements)
            {
                var comment = new Comment();
                comment.Id = GetIntAttributeValue(element, "Id");
                comment.PostId = GetIntAttributeValue(element, "PostId");
                comment.Text = GetStringAttributeValue(element, "Text");
                comment.CreationDate = GetDateAttributeValue(element, "CreationDate");
                comment.UserId = GetIntAttributeValue(element, "UserId");
                result.Add(comment);
            }
            return result;
        }        
    }
}