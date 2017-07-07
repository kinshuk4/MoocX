using System;
using System.Collections.Generic;
using System.Xml.Linq;

namespace StackOverflowDumpCodeBuilder
{
    public class VoteMapper: BaseMapper
    {
        public IEnumerable<Vote> Map(IEnumerable<XElement> elements)
        {            
            var result = new List<Vote>();
            foreach (XElement element in elements)
            {
                var vote = new Vote();
                vote.Id = GetIntAttributeValue(element, "Id");
                vote.PostId = GetIntAttributeValue(element, "PostId");
                vote.VoteTypeId = GetIntAttributeValue(element, "VoteTypeId");
                vote.CreationDate = GetDateAttributeValue(element, "CreationDate");
                vote.UserId = GetIntAttributeValue(element, "UserId");
                result.Add(vote);
            }
            return result;
        }
    }
}